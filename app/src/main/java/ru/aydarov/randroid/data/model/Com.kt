//package ru.aydarov.randroid.data.model
//
//
//import com.google.gson.annotations.SerializedName
//
//data class Com(
//    @SerializedName("data")
//    val `data`: Data,
//    @SerializedName("kind")
//    val kind: String
//) {
//    data class Data(
//        @SerializedName("after")
//        val after: Any,
//        @SerializedName("before")
//        val before: Any,
//        @SerializedName("children")
//        val children: List<Children>,
//        @SerializedName("dist")
//        val dist: Any,
//        @SerializedName("modhash")
//        val modhash: Any
//    ) {
//        data class Children(
//            @SerializedName("data")
//            val `data`: Data,
//            @SerializedName("kind")
//            val kind: String
//        ) {
//            data class Data(
//                @SerializedName("all_awardings")
//                val allAwardings: List<Any>,
//                @SerializedName("approved_at_utc")
//                val approvedAtUtc: Any,
//                @SerializedName("approved_by")
//                val approvedBy: Any,
//                @SerializedName("archived")
//                val archived: Boolean,
//                @SerializedName("associated_award")
//                val associatedAward: Any,
//                @SerializedName("author")
//                val author: String,
//                @SerializedName("author_flair_background_color")
//                val authorFlairBackgroundColor: Any,
//                @SerializedName("author_flair_css_class")
//                val authorFlairCssClass: Any,
//                @SerializedName("author_flair_richtext")
//                val authorFlairRichtext: List<Any>,
//                @SerializedName("author_flair_template_id")
//                val authorFlairTemplateId: Any,
//                @SerializedName("author_flair_text")
//                val authorFlairText: Any,
//                @SerializedName("author_flair_text_color")
//                val authorFlairTextColor: Any,
//                @SerializedName("author_flair_type")
//                val authorFlairType: String,
//                @SerializedName("author_fullname")
//                val authorFullname: String,
//                @SerializedName("author_patreon_flair")
//                val authorPatreonFlair: Boolean,
//                @SerializedName("author_premium")
//                val authorPremium: Boolean,
//                @SerializedName("awarders")
//                val awarders: List<Any>,
//                @SerializedName("banned_at_utc")
//                val bannedAtUtc: Any,
//                @SerializedName("banned_by")
//                val bannedBy: Any,
//                @SerializedName("body")
//                val body: String,
//                @SerializedName("body_html")
//                val bodyHtml: String,
//                @SerializedName("can_gild")
//                val canGild: Boolean,
//                @SerializedName("can_mod_post")
//                val canModPost: Boolean,
//                @SerializedName("collapsed")
//                val collapsed: Boolean,
//                @SerializedName("collapsed_because_crowd_control")
//                val collapsedBecauseCrowdControl: Any,
//                @SerializedName("collapsed_reason")
//                val collapsedReason: Any,
//                @SerializedName("controversiality")
//                val controversiality: Int,
//                @SerializedName("created")
//                val created: Double,
//                @SerializedName("created_utc")
//                val createdUtc: Double,
//                @SerializedName("depth")
//                val depth: Int,
//                @SerializedName("distinguished")
//                val distinguished: Any,
//                @SerializedName("downs")
//                val downs: Int,
//                @SerializedName("edited")
//                val edited: Boolean,
//                @SerializedName("gilded")
//                val gilded: Int,
//                @SerializedName("gildings")
//                val gildings: Gildings,
//                @SerializedName("id")
//                val id: String,
//                @SerializedName("is_submitter")
//                val isSubmitter: Boolean,
//                @SerializedName("likes")
//                val likes: Any,
//                @SerializedName("link_id")
//                val linkId: String,
//                @SerializedName("locked")
//                val locked: Boolean,
//                @SerializedName("mod_note")
//                val modNote: Any,
//                @SerializedName("mod_reason_by")
//                val modReasonBy: Any,
//                @SerializedName("mod_reason_title")
//                val modReasonTitle: Any,
//                @SerializedName("mod_reports")
//                val modReports: List<Any>,
//                @SerializedName("name")
//                val name: String,
//                @SerializedName("no_follow")
//                val noFollow: Boolean,
//                @SerializedName("num_reports")
//                val numReports: Any,
//                @SerializedName("parent_id")
//                val parentId: String,
//                @SerializedName("permalink")
//                val permalink: String,
//                @SerializedName("removal_reason")
//                val removalReason: Any,
//                @SerializedName("replies")
//                val replies: Replies,
//                @SerializedName("report_reasons")
//                val reportReasons: Any,
//                @SerializedName("saved")
//                val saved: Boolean,
//                @SerializedName("score")
//                val score: Int,
//                @SerializedName("score_hidden")
//                val scoreHidden: Boolean,
//                @SerializedName("send_replies")
//                val sendReplies: Boolean,
//                @SerializedName("stickied")
//                val stickied: Boolean,
//                @SerializedName("subreddit")
//                val subreddit: String,
//                @SerializedName("subreddit_id")
//                val subredditId: String,
//                @SerializedName("subreddit_name_prefixed")
//                val subredditNamePrefixed: String,
//                @SerializedName("subreddit_type")
//                val subredditType: String,
//                @SerializedName("total_awards_received")
//                val totalAwardsReceived: Int,
//                @SerializedName("ups")
//                val ups: Int,
//                @SerializedName("user_reports")
//                val userReports: List<Any>
//            ) {
//                class Gildings(
//                )
//
//                data class Replies(
//                    @SerializedName("data")
//                    val `data`: Data,
//                    @SerializedName("kind")
//                    val kind: String
//                ) {
//                    data class Data(
//                        @SerializedName("after")
//                        val after: Any,
//                        @SerializedName("before")
//                        val before: Any,
//                        @SerializedName("children")
//                        val children: List<Children>,
//                        @SerializedName("dist")
//                        val dist: Any,
//                        @SerializedName("modhash")
//                        val modhash: Any
//                    ) {
//                        data class Children(
//                            @SerializedName("data")
//                            val `data`: Data,
//                            @SerializedName("kind")
//                            val kind: String
//                        ) {
//                            data class Data(
//                                @SerializedName("all_awardings")
//                                val allAwardings: List<Any>,
//                                @SerializedName("approved_at_utc")
//                                val approvedAtUtc: Any,
//                                @SerializedName("approved_by")
//                                val approvedBy: Any,
//                                @SerializedName("archived")
//                                val archived: Boolean,
//                                @SerializedName("associated_award")
//                                val associatedAward: Any,
//                                @SerializedName("author")
//                                val author: String,
//                                @SerializedName("author_flair_background_color")
//                                val authorFlairBackgroundColor: Any,
//                                @SerializedName("author_flair_css_class")
//                                val authorFlairCssClass: Any,
//                                @SerializedName("author_flair_richtext")
//                                val authorFlairRichtext: List<Any>,
//                                @SerializedName("author_flair_template_id")
//                                val authorFlairTemplateId: Any,
//                                @SerializedName("author_flair_text")
//                                val authorFlairText: Any,
//                                @SerializedName("author_flair_text_color")
//                                val authorFlairTextColor: Any,
//                                @SerializedName("author_flair_type")
//                                val authorFlairType: String,
//                                @SerializedName("author_fullname")
//                                val authorFullname: String,
//                                @SerializedName("author_patreon_flair")
//                                val authorPatreonFlair: Boolean,
//                                @SerializedName("author_premium")
//                                val authorPremium: Boolean,
//                                @SerializedName("awarders")
//                                val awarders: List<Any>,
//                                @SerializedName("banned_at_utc")
//                                val bannedAtUtc: Any,
//                                @SerializedName("banned_by")
//                                val bannedBy: Any,
//                                @SerializedName("body")
//                                val body: String,
//                                @SerializedName("body_html")
//                                val bodyHtml: String,
//                                @SerializedName("can_gild")
//                                val canGild: Boolean,
//                                @SerializedName("can_mod_post")
//                                val canModPost: Boolean,
//                                @SerializedName("collapsed")
//                                val collapsed: Boolean,
//                                @SerializedName("collapsed_because_crowd_control")
//                                val collapsedBecauseCrowdControl: Any,
//                                @SerializedName("collapsed_reason")
//                                val collapsedReason: Any,
//                                @SerializedName("controversiality")
//                                val controversiality: Int,
//                                @SerializedName("created")
//                                val created: Double,
//                                @SerializedName("created_utc")
//                                val createdUtc: Double,
//                                @SerializedName("depth")
//                                val depth: Int,
//                                @SerializedName("distinguished")
//                                val distinguished: Any,
//                                @SerializedName("downs")
//                                val downs: Int,
//                                @SerializedName("edited")
//                                val edited: Double,
//                                @SerializedName("gilded")
//                                val gilded: Int,
//                                @SerializedName("gildings")
//                                val gildings: Gildings,
//                                @SerializedName("id")
//                                val id: String,
//                                @SerializedName("is_submitter")
//                                val isSubmitter: Boolean,
//                                @SerializedName("likes")
//                                val likes: Any,
//                                @SerializedName("link_id")
//                                val linkId: String,
//                                @SerializedName("locked")
//                                val locked: Boolean,
//                                @SerializedName("mod_note")
//                                val modNote: Any,
//                                @SerializedName("mod_reason_by")
//                                val modReasonBy: Any,
//                                @SerializedName("mod_reason_title")
//                                val modReasonTitle: Any,
//                                @SerializedName("mod_reports")
//                                val modReports: List<Any>,
//                                @SerializedName("name")
//                                val name: String,
//                                @SerializedName("no_follow")
//                                val noFollow: Boolean,
//                                @SerializedName("num_reports")
//                                val numReports: Any,
//                                @SerializedName("parent_id")
//                                val parentId: String,
//                                @SerializedName("permalink")
//                                val permalink: String,
//                                @SerializedName("removal_reason")
//                                val removalReason: Any,
//                                @SerializedName("replies")
//                                val replies: Replies,
//                                @SerializedName("report_reasons")
//                                val reportReasons: Any,
//                                @SerializedName("saved")
//                                val saved: Boolean,
//                                @SerializedName("score")
//                                val score: Int,
//                                @SerializedName("score_hidden")
//                                val scoreHidden: Boolean,
//                                @SerializedName("send_replies")
//                                val sendReplies: Boolean,
//                                @SerializedName("stickied")
//                                val stickied: Boolean,
//                                @SerializedName("subreddit")
//                                val subreddit: String,
//                                @SerializedName("subreddit_id")
//                                val subredditId: String,
//                                @SerializedName("subreddit_name_prefixed")
//                                val subredditNamePrefixed: String,
//                                @SerializedName("subreddit_type")
//                                val subredditType: String,
//                                @SerializedName("total_awards_received")
//                                val totalAwardsReceived: Int,
//                                @SerializedName("ups")
//                                val ups: Int,
//                                @SerializedName("user_reports")
//                                val userReports: List<Any>
//                            ) {
//                                class Gildings(
//                                )
//
//                                data class Replies(
//                                    @SerializedName("data")
//                                    val `data`: Data,
//                                    @SerializedName("kind")
//                                    val kind: String
//                                ) {
//                                    data class Data(
//                                        @SerializedName("after")
//                                        val after: Any,
//                                        @SerializedName("before")
//                                        val before: Any,
//                                        @SerializedName("children")
//                                        val children: List<Children>,
//                                        @SerializedName("dist")
//                                        val dist: Any,
//                                        @SerializedName("modhash")
//                                        val modhash: Any
//                                    ) {
//                                        data class Children(
//                                            @SerializedName("data")
//                                            val `data`: Data,
//                                            @SerializedName("kind")
//                                            val kind: String
//                                        ) {
//                                            data class Data(
//                                                @SerializedName("all_awardings")
//                                                val allAwardings: List<Any>,
//                                                @SerializedName("approved_at_utc")
//                                                val approvedAtUtc: Any,
//                                                @SerializedName("approved_by")
//                                                val approvedBy: Any,
//                                                @SerializedName("archived")
//                                                val archived: Boolean,
//                                                @SerializedName("associated_award")
//                                                val associatedAward: Any,
//                                                @SerializedName("author")
//                                                val author: String,
//                                                @SerializedName("author_flair_background_color")
//                                                val authorFlairBackgroundColor: String,
//                                                @SerializedName("author_flair_css_class")
//                                                val authorFlairCssClass: String,
//                                                @SerializedName("author_flair_richtext")
//                                                val authorFlairRichtext: List<AuthorFlairRichtext>,
//                                                @SerializedName("author_flair_template_id")
//                                                val authorFlairTemplateId: Any,
//                                                @SerializedName("author_flair_text")
//                                                val authorFlairText: String,
//                                                @SerializedName("author_flair_text_color")
//                                                val authorFlairTextColor: String,
//                                                @SerializedName("author_flair_type")
//                                                val authorFlairType: String,
//                                                @SerializedName("author_fullname")
//                                                val authorFullname: String,
//                                                @SerializedName("author_patreon_flair")
//                                                val authorPatreonFlair: Boolean,
//                                                @SerializedName("author_premium")
//                                                val authorPremium: Boolean,
//                                                @SerializedName("awarders")
//                                                val awarders: List<Any>,
//                                                @SerializedName("banned_at_utc")
//                                                val bannedAtUtc: Any,
//                                                @SerializedName("banned_by")
//                                                val bannedBy: Any,
//                                                @SerializedName("body")
//                                                val body: String,
//                                                @SerializedName("body_html")
//                                                val bodyHtml: String,
//                                                @SerializedName("can_gild")
//                                                val canGild: Boolean,
//                                                @SerializedName("can_mod_post")
//                                                val canModPost: Boolean,
//                                                @SerializedName("collapsed")
//                                                val collapsed: Boolean,
//                                                @SerializedName("collapsed_because_crowd_control")
//                                                val collapsedBecauseCrowdControl: Any,
//                                                @SerializedName("collapsed_reason")
//                                                val collapsedReason: Any,
//                                                @SerializedName("controversiality")
//                                                val controversiality: Int,
//                                                @SerializedName("created")
//                                                val created: Double,
//                                                @SerializedName("created_utc")
//                                                val createdUtc: Double,
//                                                @SerializedName("depth")
//                                                val depth: Int,
//                                                @SerializedName("distinguished")
//                                                val distinguished: Any,
//                                                @SerializedName("downs")
//                                                val downs: Int,
//                                                @SerializedName("edited")
//                                                val edited: Boolean,
//                                                @SerializedName("gilded")
//                                                val gilded: Int,
//                                                @SerializedName("gildings")
//                                                val gildings: Gildings,
//                                                @SerializedName("id")
//                                                val id: String,
//                                                @SerializedName("is_submitter")
//                                                val isSubmitter: Boolean,
//                                                @SerializedName("likes")
//                                                val likes: Any,
//                                                @SerializedName("link_id")
//                                                val linkId: String,
//                                                @SerializedName("locked")
//                                                val locked: Boolean,
//                                                @SerializedName("mod_note")
//                                                val modNote: Any,
//                                                @SerializedName("mod_reason_by")
//                                                val modReasonBy: Any,
//                                                @SerializedName("mod_reason_title")
//                                                val modReasonTitle: Any,
//                                                @SerializedName("mod_reports")
//                                                val modReports: List<Any>,
//                                                @SerializedName("name")
//                                                val name: String,
//                                                @SerializedName("no_follow")
//                                                val noFollow: Boolean,
//                                                @SerializedName("num_reports")
//                                                val numReports: Any,
//                                                @SerializedName("parent_id")
//                                                val parentId: String,
//                                                @SerializedName("permalink")
//                                                val permalink: String,
//                                                @SerializedName("removal_reason")
//                                                val removalReason: Any,
//                                                @SerializedName("replies")
//                                                val replies: String,
//                                                @SerializedName("report_reasons")
//                                                val reportReasons: Any,
//                                                @SerializedName("saved")
//                                                val saved: Boolean,
//                                                @SerializedName("score")
//                                                val score: Int,
//                                                @SerializedName("score_hidden")
//                                                val scoreHidden: Boolean,
//                                                @SerializedName("send_replies")
//                                                val sendReplies: Boolean,
//                                                @SerializedName("stickied")
//                                                val stickied: Boolean,
//                                                @SerializedName("subreddit")
//                                                val subreddit: String,
//                                                @SerializedName("subreddit_id")
//                                                val subredditId: String,
//                                                @SerializedName("subreddit_name_prefixed")
//                                                val subredditNamePrefixed: String,
//                                                @SerializedName("subreddit_type")
//                                                val subredditType: String,
//                                                @SerializedName("total_awards_received")
//                                                val totalAwardsReceived: Int,
//                                                @SerializedName("ups")
//                                                val ups: Int,
//                                                @SerializedName("user_reports")
//                                                val userReports: List<Any>
//                                            ) {
//                                                data class AuthorFlairRichtext(
//                                                    @SerializedName("e")
//                                                    val e: String,
//                                                    @SerializedName("t")
//                                                    val t: String
//                                                )
//
//                                                class Gildings(
//                                                )
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}