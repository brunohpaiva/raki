package codes.bruno.raki.core.data.database.util

import androidx.paging.PagingSource
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicBoolean

internal class PagingSourceInvalidationTracker(
    private val pagingSource: PagingSource<*, *>,
    private val tables: Array<String>,
) : InvalidationTracker.Observer(tables) {

    private val registered = AtomicBoolean(false)

    override fun onInvalidated(tables: Set<String>) {
        pagingSource.invalidate()
    }

    fun register(db: RoomDatabase) {
        if (registered.compareAndSet(false, true)) {
            db.invalidationTracker.addObserver(WeakObserver(db.invalidationTracker, tables, this))
        }
    }

}

private class WeakObserver(
    private val tracker: InvalidationTracker,
    tables: Array<String>,
    delegate: InvalidationTracker.Observer,
) : InvalidationTracker.Observer(tables) {

    private val delegateRef = WeakReference(delegate)

    override fun onInvalidated(tables: Set<String>) {
        val observer = delegateRef.get()
        if (observer == null) {
            tracker.removeObserver(this)
        } else {
            observer.onInvalidated(tables)
        }
    }

}