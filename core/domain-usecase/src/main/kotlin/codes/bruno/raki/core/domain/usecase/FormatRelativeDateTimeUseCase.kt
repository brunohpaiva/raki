package codes.bruno.raki.core.domain.usecase

import java.time.Duration
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.Temporal
import javax.inject.Inject

class FormatRelativeDateTimeUseCase @Inject internal constructor() {

    @Suppress("NewApi")
    operator fun invoke(then: Temporal, now: Temporal): String {
        val duration = Duration.between(then, now)
        val days = duration.toDays()

        if (duration.isNegative) {
            TODO("support future dates")
        }

        // TODO: i18n
        return when {
            days >= 7 -> {
                val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                formatter.format(then)
            }
            days >= 1 -> "${days}d"
            duration.toHours() >= 1 -> "${duration.toHours()}h"
            duration.toMinutes() >= 1 -> "${duration.toMinutes()}m"
            else -> "${duration.toSeconds()}s"
        }
    }

}
