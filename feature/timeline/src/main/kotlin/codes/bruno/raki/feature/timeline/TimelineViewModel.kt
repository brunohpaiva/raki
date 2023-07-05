package codes.bruno.raki.feature.timeline

import android.graphics.Typeface
import android.text.Spanned
import android.text.SpannedString
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.SubscriptSpan
import android.text.style.SuperscriptSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import androidx.core.text.getSpans
import androidx.core.text.parseAsHtml
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import codes.bruno.raki.core.domain.model.MediaAttachmentType
import codes.bruno.raki.core.domain.model.TimelineStatus
import codes.bruno.raki.core.domain.usecase.FetchTimelineUseCase
import codes.bruno.raki.core.domain.usecase.FormatRelativeDateTimeUseCase
import codes.bruno.raki.core.domain.usecase.ToggleStatusBookmarkUseCase
import codes.bruno.raki.core.domain.usecase.ToggleStatusFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
internal class TimelineViewModel @Inject constructor(
    fetchTimelineUseCase: FetchTimelineUseCase,
    private val formatRelativeDateTimeUseCase: FormatRelativeDateTimeUseCase,
    private val toggleStatusFavouriteUseCase: ToggleStatusFavouriteUseCase,
    private val toggleStatusBookmarkUseCase: ToggleStatusBookmarkUseCase,
) : ViewModel() {

    val timeline = fetchTimelineUseCase()
        .map { pagingData ->
            val currentTime = OffsetDateTime.now()
            pagingData.map { mapToUiModel(it, currentTime) }
        }
        .cachedIn(viewModelScope)

    fun toggleFavourite(statusId: String) = viewModelScope.launch {
        toggleStatusFavouriteUseCase(statusId)
    }

    fun toggleBookmark(statusId: String) = viewModelScope.launch {
        toggleStatusBookmarkUseCase(statusId)
    }

    private suspend fun mapToUiModel(
        status: TimelineStatus,
        currentTime: OffsetDateTime,
    ): TimelineStatusUi {
        return TimelineStatusUi(
            id = status.id,
            authorAvatarUrl = status.authorAvatarUrl,
            authorDisplayName = status.authorDisplayName,
            authorAcct = status.authorAcct,
            rebloggerAvatarUrl = status.rebloggerAvatarUrl,
            rebloggerDisplayName = status.rebloggerDisplayName,
            rebloggerAcct = status.rebloggerAcct,
            relativeCreatedAt = formatRelativeDateTimeUseCase(status.createdAt, currentTime),
            content = parseContent(status.content),
            mediaAttachments = status.mediaAttachments.map {
                StatusMediaAttachmentUi(
                    id = it.id,
                    type = it.type,
                    url = it.url,
                    previewUrl = it.previewUrl,
                    description = it.description,
                )
            },
            favourited = status.favourited,
            bookmarked = status.bookmarked,
        )
    }

    @OptIn(ExperimentalTextApi::class)
    private suspend fun parseContent(htmlContent: String) = withContext(Dispatchers.IO) {
        val spanned = htmlContent
            .replace("<br\\s?/>\\s?".toRegex(), "<br/>&nbsp;")
            .parseAsHtml()
            .dropLastWhile { it.isWhitespace() } as? Spanned ?: SpannedString("") // TODO
        val spans = with(spanned) {
            getSpans<Any>(0, length).map {
                Triple(it, getSpanStart(it), getSpanEnd(it))
            }
        }

        buildAnnotatedString {
            append(spanned.toString())

            for ((span, start, end) in spans) {
                if (span is URLSpan) {
                    addUrlAnnotation(
                        urlAnnotation = UrlAnnotation(span.url),
                        start = start,
                        end = end,
                    )
                }

                val style = when (span) {
                    is StyleSpan -> when (span.style) {
                        Typeface.BOLD -> SpanStyle(fontWeight = FontWeight.Bold)
                        Typeface.ITALIC -> SpanStyle(fontStyle = FontStyle.Italic)
                        Typeface.BOLD_ITALIC -> SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic,
                        )

                        else -> continue
                    }

                    is UnderlineSpan -> SpanStyle(textDecoration = TextDecoration.Underline)
                    is ForegroundColorSpan -> SpanStyle(color = Color(span.foregroundColor))
                    is StrikethroughSpan -> SpanStyle(textDecoration = TextDecoration.LineThrough)
                    is SuperscriptSpan -> SpanStyle(baselineShift = BaselineShift.Superscript)
                    is SubscriptSpan -> SpanStyle(baselineShift = BaselineShift.Subscript)
                    is URLSpan -> SpanStyle(
                        textDecoration = TextDecoration.Underline,
                        color = Color.Blue, // TODO
                    )

                    else -> continue
                }

                addStyle(style, start, end)
            }
        }
    }

}

internal data class TimelineStatusUi(
    val id: String,
    val authorAvatarUrl: String,
    val authorDisplayName: String,
    val authorAcct: String,
    val rebloggerAvatarUrl: String?,
    val rebloggerDisplayName: String?,
    val rebloggerAcct: String?,
    val relativeCreatedAt: String,
    val content: AnnotatedString,
    val mediaAttachments: List<StatusMediaAttachmentUi>,
    val favourited: Boolean,
    val bookmarked: Boolean,
)

internal data class StatusMediaAttachmentUi(
    val id: String,
    val type: MediaAttachmentType,
    val url: String,
    val previewUrl: String,
    val description: String?,
)