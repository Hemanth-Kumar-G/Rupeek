package com.hemanth.getMyParking.data.model


import com.google.gson.annotations.SerializedName

data class BookResponse(
    @SerializedName("items")
    var items: List<Item>?=null,
    @SerializedName("kind")
    var kind: String,
    @SerializedName("totalItems")
    var totalItems: Double
) {
    data class Item(
        @SerializedName("accessInfo")
        var accessInfo: AccessInfo,
        @SerializedName("etag")
        var etag: String,
        @SerializedName("id")
        var id: String,
        @SerializedName("kind")
        var kind: String,
        @SerializedName("saleInfo")
        var saleInfo: SaleInfo,
        @SerializedName("searchInfo")
        var searchInfo: SearchInfo,
        @SerializedName("selfLink")
        var selfLink: String,
        @SerializedName("volumeInfo")
        var volumeInfo: VolumeInfo
    ) {
        data class AccessInfo(
            @SerializedName("accessViewStatus")
            var accessViewStatus: String,
            @SerializedName("country")
            var country: String,
            @SerializedName("embeddable")
            var embeddable: Boolean,
            @SerializedName("epub")
            var epub: Epub,
            @SerializedName("pdf")
            var pdf: Pdf,
            @SerializedName("publicDomain")
            var publicDomain: Boolean,
            @SerializedName("quoteSharingAllowed")
            var quoteSharingAllowed: Boolean,
            @SerializedName("textToSpeechPermission")
            var textToSpeechPermission: String,
            @SerializedName("viewability")
            var viewability: String,
            @SerializedName("webReaderLink")
            var webReaderLink: String
        ) {
            data class Epub(
                @SerializedName("acsTokenLink")
                var acsTokenLink: String,
                @SerializedName("isAvailable")
                var isAvailable: Boolean
            )

            data class Pdf(
                @SerializedName("acsTokenLink")
                var acsTokenLink: String,
                @SerializedName("isAvailable")
                var isAvailable: Boolean
            )
        }

        data class SaleInfo(
            @SerializedName("buyLink")
            var buyLink: String,
            @SerializedName("country")
            var country: String,
            @SerializedName("isEbook")
            var isEbook: Boolean,
            @SerializedName("listPrice")
            var listPrice: ListPrice,
            @SerializedName("offers")
            var offers: List<Offer>,
            @SerializedName("retailPrice")
            var retailPrice: RetailPrice,
            @SerializedName("saleability")
            var saleability: String
        ) {
            data class ListPrice(
                @SerializedName("amount")
                var amount: Double,
                @SerializedName("currencyCode")
                var currencyCode: String
            )

            data class Offer(
                @SerializedName("finskyOfferType")
                var finskyOfferType: Double,
                @SerializedName("listPrice")
                var listPrice: ListPrice,
                @SerializedName("retailPrice")
                var retailPrice: RetailPrice
            ) {
                data class ListPrice(
                    @SerializedName("amountInMicros")
                    var amountInMicros: Double,
                    @SerializedName("currencyCode")
                    var currencyCode: String
                )

                data class RetailPrice(
                    @SerializedName("amountInMicros")
                    var amountInMicros: Double,
                    @SerializedName("currencyCode")
                    var currencyCode: String
                )
            }

            data class RetailPrice(
                @SerializedName("amount")
                var amount: Double,
                @SerializedName("currencyCode")
                var currencyCode: String
            )
        }

        data class SearchInfo(
            @SerializedName("textSnippet")
            var textSnippet: String
        )

        data class VolumeInfo(
            @SerializedName("allowAnonLogging")
            var allowAnonLogging: Boolean,
            @SerializedName("authors")
            var authors: List<String>,
            @SerializedName("averageRating")
            var averageRating: Double,
            @SerializedName("canonicalVolumeLink")
            var canonicalVolumeLink: String,
            @SerializedName("categories")
            var categories: List<String>,
            @SerializedName("contentVersion")
            var contentVersion: String,
            @SerializedName("description")
            var description: String,
            @SerializedName("imageLinks")
            var imageLinks: ImageLinks,
            @SerializedName("industryIdentifiers")
            var industryIdentifiers: List<IndustryIdentifier>,
            @SerializedName("infoLink")
            var infoLink: String,
            @SerializedName("language")
            var language: String,
            @SerializedName("maturityRating")
            var maturityRating: String,
            @SerializedName("pageCount")
            var pageCount: Double,
            @SerializedName("panelizationSummary")
            var panelizationSummary: PanelizationSummary,
            @SerializedName("previewLink")
            var previewLink: String,
            @SerializedName("printType")
            var printType: String,
            @SerializedName("publishedDate")
            var publishedDate: String,
            @SerializedName("publisher")
            var publisher: String,
            @SerializedName("ratingsCount")
            var ratingsCount: Double,
            @SerializedName("readingModes")
            var readingModes: ReadingModes,
            @SerializedName("subtitle")
            var subtitle: String,
            @SerializedName("title")
            var title: String
        ) {
            data class ImageLinks(
                @SerializedName("smallThumbnail")
                var smallThumbnail: String,
                @SerializedName("thumbnail")
                var thumbnail: String
            )

            data class IndustryIdentifier(
                @SerializedName("identifier")
                var identifier: String,
                @SerializedName("type")
                var type: String
            )

            data class PanelizationSummary(
                @SerializedName("containsEpubBubbles")
                var containsEpubBubbles: Boolean,
                @SerializedName("containsImageBubbles")
                var containsImageBubbles: Boolean
            )

            data class ReadingModes(
                @SerializedName("image")
                var image: Boolean,
                @SerializedName("text")
                var text: Boolean
            )
        }
    }
}