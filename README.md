English coming soon....

## EngineerReview：エンジニアのための書籍レビューサービス

<details open="open">
  <summary>目次</summary>
  <ol>
    <li>
      <a href="#プロジェクトについて">プロジェクトについて</a>
      <ul>
        <li><a href="#利用技術">利用技術</a></li>
      </ul>
    </li>
    <li>
      <a href="#アプリケーション概要">アプリケーション概要</a>
      <ul>
        <li><a href="#クラス構成図">クラス構成図</a></li>
        <li>
            <a href="#DDDによる設計">DDDによる設計</a>
                <ul>
                    <li><a href="#Entity">Entity</a></li>
                    <li><a href="#ValueObject">ValueObject</a></li>
                    <li><a href="#Repository">Repository</a></li>
                    <li><a href="#Factory">Factory</a></li>
                    <li><a href="#Collection">Collection</a></li>
                </ul>
        </li>
        <li><a href="#Controller">Controller</a></li>
      </ul>
    </li>
    <li><a href="#コンタクト">コンタクト</a></li>
    <li><a href="#謝辞">謝辞</a></li>
  </ol>
</details>


## プロジェクトについて

個人プロジェクトとして取り組んでいるエンジニア向けの書籍レビューサービスで、軽量DDDで設計をしています。今年中のリリースを目指して取り組んでいます。

* 個人の趣味として
  * エンジニアリングは純粋に楽しいので。
* 人々に利用してもらえるサービスを目指して
  * 自身もエンジニアとして日々勉強に励む中、技術書などの書籍情報が集約されていない・それらが技術書として良いものなのかを判断する術が確立されていないという課題を感じ続けている。同じ様な悩みを持つエンジニアの一助になれる様なサービスを目指す。
* 技術研鑽として
  * モダンな技術や興味のある技術を実際に利用し自分のスキルとすることを目指す。
* ポートフォリオとして
  * エンジニアとして経験の浅い中、自身の技術力・モチベーションを示す転職材料として活かす。
  
*現在の進捗：サーバサイドをDDD（ドメイン駆動開発）にて開発中*


### 利用技術

* サーバサイド
  * Spring Boot：2.4.2
    * Kotlin：1.3.21
* フロントエンド（予定）
  * React
    * TypeScript
* インフラストラクチャ（予定）
  * AWS
  

## アプリケーション概要

### クラス構成図
![ClassDiagram](https://user-images.githubusercontent.com/54053792/107140996-50063a00-6969-11eb-9d61-b1f6c6d9eeb9.png)

* 4つのEntityとそれに属するValueObjectで構成
* 関連するEntityについてはManyToOneの関係で外部参照を付与

### DDDによる設計

#### Entity

DDDの各要素（ValueObject、Repository、Factory、Collection）の主体となるドメインモデル。

```kotlin
@Entity
@Table(name = "book")
class Book(
        @ApiModelProperty(value = "AuthorId", required = true)
        @ManyToOne
        @JoinColumn(name = "author_id")
        var author: Author,
        @ApiModelProperty(value = "Title", required = true)
        @Embedded
        var title: BookTitle,
        @ApiModelProperty(value = "Category", required = true)
        @Embedded
        var category: BookCategory,
        @ApiModelProperty(value = "Score", required = true)
        @Embedded
        var score: BookScore,
        @ApiModelProperty(value = "URL", required = true)
        @Embedded
        var url: BookUrl,
        @ApiModelProperty(value = "Description", required = true)
        @Embedded
        var description: BookDescription,
        @ApiModelProperty(value = "PublishedAt", required = true)
        @Embedded
        var publishedAt: BookPublishedAt) : AbstractEntity<BookId>() {

    /**
     * @return Value Object ID
     */
    override fun id() = BookId(this.id)

    /**
     * @return saved entity
     */
    override fun save() = BookRepository.save(this)

    companion object {
        /**
         * call `new` method process provided by Factory
         *
         * @return Book
         */
        fun new(params: RequestParams, isNew: Boolean = true, id: BookId = BookId.getUnsavedId()) =
                BookFactory.new(params, isNew, id)
    }
}
```

* 各ValueObject要素によって構成される
  * ValueObjectをプライマリコンストラクタに引き渡すことでEntityを生成する
* ドメインのビジネスロジックに関わらない処理は関連クラスに移譲する
  * 生成処理はFactoryに移譲
  * 保存処理はRepositoryに移譲
  
#### ValueObject

Entityのドメインモデルを構成する各要素

```kotlin
@Embeddable
data class BookTitle(
        @Column(name = "title", nullable = false)
        override var value: String) : AbstractValueObject<String>() {

    /**
     * validation rule
     */
    init {
        validate(this) {
            validate(BookTitle::value).hasSize(min = 1, max = 50)
        }
    }
}
```

* ドメインモデルを構成するオブジェクト
  * 対応するprimitive型をプライマリコンストラクタに引き渡すことでValueObjectを生成する
    * 引き渡された値を`value`として保持する
  * ValueObjectに引き渡す値のバリデーションは [Valiktor](https://github.com/valiktor/valiktor) を用いて行こなう
    * 生成時にバリデーションを行うことで、ValueObjectの保持する値の妥当性を担保する
* ValueObjectはdataクラスとして実装する  
  * 振る舞いを持たせたい場合には各ValueObjectクラス上にてメソッドを定義する

#### Repository

ドメインモデルのCRUD処理はRepositoryの責務として実装する

```kotlin
@Repository
private interface BookRepositoryBase : JpaRepository<Book, Long>

/**
 * repository class which has static methods access to repository interface
 */
class BookRepository {
    companion object {
        /**
         * resolve interface container and acquire that statistically
         */
        private fun repository(): BookRepositoryBase = Resolver.resolve(BookRepositoryBase::class.java)

        /**
         * save [entity]
         */
        fun save(entity: Book): Book {
            return repository().saveAndFlush(entity)
        }

        /**
         * find all entities
         */
        fun findAll(): BookCollection = repository().findAll().toCollection()

        /**
         * find entity by [id]
         */
        fun findById(id: BookId): Book =
                repository().findById(id.value).orElseThrow(::NoSuchElementException)


        /**
         * delete [entity]
         */
        fun delete(entity: Book) = repository().delete(entity)
    }
}
```

* Repositoryのメソッドにstaticな状態でアクセスできる様にcompanion objectとして実装する
* JpaRepositoryを基底とし、ラッパとしてドメインモデルに属するRepositoryクラスを作成
* resolveユーティリティを用いることで、基底となるJpaRepositoryのインスタンスをDIコンテナから取得する


#### Factory

Entityの生成処理に関する処理はFactoryの責務として実装する

```kotlin
class BookFactory {
    companion object {
        /**
         * create or update instance by referring [params]
         * if [isNew] is false, need [id] to specify the target entity
         *
         * @return [Book]
         */
        fun new(params: RequestParams, isNew: Boolean, id: BookId): Book {
            val author = AuthorId(params.getValue("authorId").toLong()).toEntity()
            val title = BookTitle(params.getValue("title"))
            val category = BookCategory(BookCategory.Categories.valueOf(params.getValue("category")))
            val score = BookScore(params.getValue("score").toDouble())
            val url = BookUrl(params.getValue("url"))
            val description = BookDescription(params.getValue("description"))
            val publishedAt = BookPublishedAt(LocalDate.parse(params.getValue("publishedAt"), DateTimeFormatter.ISO_DATE))

            val entity: Book
            when (isNew) {
                // when the entity is newly created, prepare new entity
                true -> {
                    entity = Book(
                            author,
                            title,
                            category,
                            score,
                            url,
                            description,
                            publishedAt
                    )
                }
                // when the existed entity is updated, set each fields with new ones
                false -> {
                    entity = BookRepository.findById(id)
                    entity.author = author
                    entity.title = title
                    entity.category = category
                    entity.score = score
                    entity.url = url
                    entity.description = description
                    entity.publishedAt = publishedAt
                }
            }
            return entity.save()
        }
    }
}
```

* フロントエンドから送信される（と想定される）パラメータを対応するValueObjectとして設定する
* フラグを`isNew`として引き渡すことで、新規時・更新時の処理をDRYに記述する
* Entityから`new`メソッドの処理を委譲する際にstaticに呼び出しを行える様、companion objectとして実装する

#### Collection

ある特定のドメインモデルを保持するリストのラッパクラス

```kotlin
/**
 * @return [BookCollection] converted from [List<Book>]
 */
fun List<Book>.toCollection(): BookCollection {
    return BookCollection(this)
}

class BookCollection(list: List<Book>) : AbstractEntityCollection<BookCollection, Book>(list) {

}
```

* ドメインモデルのロジックをカプセル化するため、複数のドメインモデルを扱う場合にはCollectionとして扱う
* Collectionに振る舞いを持たせたい場合には、メソッドの返却型を自身のCollectionクラスとする
  * メソッドチェインの形で"閉じた操作"を実現できる
  * こうすることでドメインモデルのロジックが外部に漏れることなくモデルの集合であるCollectionへの操作を実現できる
  

### API

各ドメインモデルのCURD処理を行うAPIをControllerとして実装する


## コンタクト

Natsume Takuya
* [Twitter：@Takuy_aaaa](https://twitter.com/Takuy_aaaa)
* [Qiita：@Takuyaaaa](https://qiita.com/Takuyaaaa)
* メールアドレス：xxtyzs@gmail.com

プロジェクトリンク：[https://github.com/Takuyaaaa/engineer_review](https://github.com/Takuyaaaa/engineer_review)


## 謝辞

* [Valiktor](https://github.com/valiktor/valiktor)