package com.spring_boot.base.collection

import com.spring_boot.base.model.entity.AbstractEntity
import java.lang.reflect.ParameterizedType

/**
 * abstract class implementing common methods of Entity Collection
 */
abstract class AbstractEntityCollection
<out S : AbstractEntityCollection<S, T>, out T : AbstractEntity<*>>(private val list: List<T>) : Iterable<T> {

    /**
     * @return true if this collection is empty, false if not
     */
    fun isEmpty(): Boolean {
        return list.isEmpty()
    }

    /**
     * @return count elements of this collection
     */
    fun count(): Int {
        return list.count()
    }

    /**
     * @return element positioned at [index]
     */
    fun get(index: Int): T? {
        return when {
            count() > index -> list[index]
            else -> null
        }
    }

    /**
     * @return sorted collection by createdDate
     */
    fun sort(): S {
        val newList = list.sortedBy { it.createdDate }
        return toCollection(newList)
    }

    /**
     * @return reversed collection
     */
    fun reverse(): S {
        val newList = list.reversed()
        return toCollection(newList)
    }

    /**
     * @return collection filtered by [predicate]
     */
    fun filter(predicate: (T) -> Boolean): S {
        val newList = list.filter(predicate).toList()
        return toCollection(newList)
    }

    /**
     * @return collection converted from [list]
     */
    protected fun <T> toCollection(list: List<T>): S {
        val klass = this.javaClass
        val type = klass.genericSuperclass as ParameterizedType
        val collectionClass =
                when (val arg = type.actualTypeArguments[0]) {
                    is ParameterizedType -> arg.rawType as Class<T>
                    else -> arg as Class<T>
                }
        val constructor = collectionClass.getConstructor(List::class.java)
        return constructor.newInstance(list) as S
    }

    /**
     * @return [Iterator] of this collection
     */
    override fun iterator(): Iterator<T> {
        return list.iterator()
    }
}