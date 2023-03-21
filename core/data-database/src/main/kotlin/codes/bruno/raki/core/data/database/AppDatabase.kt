package codes.bruno.raki.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import codes.bruno.raki.core.data.database.dao.TimelineDao
import codes.bruno.raki.core.data.database.entity.Account
import codes.bruno.raki.core.data.database.entity.Status
import codes.bruno.raki.core.data.database.entity.StatusMediaAttachment

@Database(
    entities = [
        Account::class,
        Status::class,
        StatusMediaAttachment::class,
    ],
    version = 1,
)
@TypeConverters(OffsetDateTimeTypeConverter::class)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun timelineDao(): TimelineDao

}