English coming soon....

## EngineerReview：エンジニアのための書籍レビューサービス
[![CircleCI](https://circleci.com/gh/Takuyaaaa/engineer_review.svg?style=svg)](https://app.circleci.com/pipelines/github/Takuyaaaa/engineer_review)

<details open="open">
  <summary>目次</summary>
  <ol>
    <li>
      <a href="#プロジェクトについて">プロジェクトについて</a>
      <ul>
        <li><a href="#利用技術">利用技術</a></li>
        <li><a href="#Dockerについて">Dockerについて</a></li>
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
        <li><a href="#API">API</a></li>
        <li><a href="#基底クラス">基底クラス</a></li>
        <li><a href="#テストケース">テストケース</a></li>
      </ul>
    </li>
    <li><a href="#コンタクト">コンタクト</a></li>
    <li><a href="#謝辞">謝辞</a></li>
  </ol>
</details>


## プロジェクトについて

個人プロジェクトとして取り組んでいるエンジニア向けの書籍レビューサービスで、軽量DDD（ドメイン駆動開発）で設計・実装を行っています。今年中のリリースを目指して取り組んでいます。

* 個人の趣味として
  * エンジニアリングは純粋に楽しい。
* 人々に利用してもらえるサービスを目指して
  * 自身もエンジニアとして日々勉強に励む中、技術書などの書籍情報が集約されていない・それらが技術書として良いものなのかを判断する信頼性の高い情報が見つからないという課題を感じ続けている。同じ様な悩みを持つエンジニアの一助になれる様なサービスを目指す。
* 技術研鑽として
  * モダンな技術や興味のある技術を実際に利用し、自分のスキルとして身につけることを目指す。
* ポートフォリオとして
  * 自身の技術力・モチベーションを示す転職材料として活用する。
  
*現在の進捗：フロントエンドをreactにて実装中


### 利用技術

* サーバサイド
  * Spring Boot：2.4.2
    * Kotlin：1.3.21
* フロントエンド（予定）
  * React
    * TypeScript
* インフラストラクチャ（予定）
  * mariadb
  * AWS
* Docker環境
  * Docker Composeによるアプリケーション環境のコンテナ化
  
### Dockerについて
* Dockerfileでフロンエンド(react)・サーバサイド(spring boot)のアプリケーションビルドを定義する
* Docker Composeで上記アプリケーション及びmariadbを起動することでコンテナ上にアプリケーションの動作環境を用意
* デプロイについてもdockerコンテナ単位で行う想定

## アプリケーション概要

### クラス構成図
![ClassDiagram](https://user-images.githubusercontent.com/54053792/107140996-50063a00-6969-11eb-9d61-b1f6c6d9eeb9.png)

* 4つのEntityとそれに属するValueObjectで構成
* 関連するEntityについてはManyToOneの関係で外部参照を付与
* 現状ではBookにAuthorを持たせているが、AuthorにBookを持たせる設計も検討している

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
        @ApiModelProperty(value = "Rating", required = true)
        @Embedded
        var rating: BookRating,
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

ドメインモデルを構成する各要素

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

Entityの作成・更新に関する処理はFactoryの責務として実装する

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
            val rating = BookRating(params.getValue("rating").toDouble())
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
                            rating,
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
                    entity.rating = rating
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

* フロントエンドから送信される各パラメータを対応するValueObjectとして設定する
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
    // no method implemented yet
}
```

* ドメインモデルのロジックをカプセル化するため、複数のドメインモデルを扱う場合にはCollectionとして扱う
* Collectionに振る舞いを持たせたい場合には、メソッドの返却型を自身のCollectionクラスとする
  * メソッドチェインの形で"閉じた操作"を実現できる
  * こうすることでドメインモデルのロジックが外部に漏れることなくモデルの集合であるCollectionへの操作を実現できる
  

### API

各ドメインモデルのCURD処理を行うAPIをControllerとして実装する

```kotlin
@RestController
@RequestMapping("/api/book/")
@Api(description = "Book Controller")
class BookController {

    @ApiOperation(value = "Get All Books", notes = "get all Books from db", response = Book::class)
    @GetMapping("")
    fun index() = BookRepository.findAll()

    @ApiOperation(value = "Create New Book", notes = "create new Book entity", response = Book::class)
    @PostMapping("")
    fun create(@RequestParam params: Map<String, String>): Book {
        val requestParams = RequestParams(params)
        return Book.new(requestParams)
    }

    @ApiOperation(value = "Get a Specific Book", notes = "get a specific Book by ID", response = Book::class)
    @GetMapping("{id}")
    fun read(@PathVariable id: Long) = BookRepository.findById(BookId(id))

    @ApiOperation(value = "Update a Specific Book", notes = "update a specific Book by request params", response = Book::class)
    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestParam params: Map<String, String>): Book {
        val requestParams = RequestParams(params)
        return Book.new(requestParams, false, BookId(id))
    }

    @ApiOperation(value = "Delete a Specific Book", notes = "delete a specific Book passed", response = Book::class)
    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) {
        val targetEntity = BookRepository.findById(BookId(id))
        return BookRepository.delete(targetEntity)
    }
}
```

* APIの仕様は`@API`・`@ApiOperation`などのアノテーションを付与してSwaggerで管理する
* Repository・Factoryメソッドを呼び出すことでドメインモデルのCRUD操作を行うAPIを実装する
* フロントエンドはReactによるSPAとして実装することを想定しているため、RESTなAPIとしてjsonを返却する
* リクエストとして送信される各パラメータを格納するクラスとして`RequestParams`を作成する
```kotlin
/**
 * Request Parameters passed through API call
 */
class RequestParams(private val params: Map<String, String>) {
    /**
     * @return string value associated with [key]
     */
    fun getValue(key: String) =
            params[key] ?: throw IllegalArgumentException("no value associated with the key:$key")
}
```

### 基底クラス

必要に応じて各クラスの基底となるクラスを実装する

* 共通処理を規定メソッドとして実装することでDRYに記述できる様にする
* 平易に新規のDDD関連クラスを追加できる様、隠蔽できる要素は規定クラス上に実装を行う
* 実際の実装については [base](https://github.com/Takuyaaaa/engineer_review/tree/main/src/main/kotlin/com/spring_boot/base) のmodel・collectionを参照
* 後述するテストケース関連クラスについても、シンプルに記述できる様になる場合には基底クラスを実装する


### テストケース

実装されたビジネスロジックについては必ず単体テストを用意する  
push時に自動でテストが行われる様、circleciを利用したCI環境を整備する

* Entity
  * テスト用Entity・ValueObjectの生成メソッド
  * 振る舞いを持つ場合には該当メソッドの単体テスト
* ValueObject
  * 生成時のバリデーションテスト
  * 振る舞いを持つ場合には該当メソッドの単体テスト
* Repository
  * JpaRepositoryに委譲している処理については動作が担保されていると考え、テストケースの実装は行わない
    * 自身でJpaRepositoryの記法に基づき自動生成したメソッドについては、念のため単体テストを行うものとする
* Controller
  * MockMvcを利用した各APIの単体テスト
  * FactoryメソッドはAPI利用時に呼び出されることを想定しているため、`testCreate`・`testUpdate`を以ってカバーされていると考える
  
  
## コンタクト

**夏目　拓哉**  
フルリモートで働くエンジニア。長野移住予定です。
* [Twitter：@Takuy_aaaa](https://twitter.com/Takuy_aaaa)
* [Qiita：@Takuyaaaa](https://qiita.com/Takuyaaaa)
* メールアドレス：xxtyzs@gmail.com

プロジェクトリンク：[https://github.com/Takuyaaaa/engineer_review](https://github.com/Takuyaaaa/engineer_review)


## 謝辞

* [Valiktor](https://github.com/valiktor/valiktor)
* [Swagger](https://swagger.io/)
* [circleci](https://circleci.com/ja/)
