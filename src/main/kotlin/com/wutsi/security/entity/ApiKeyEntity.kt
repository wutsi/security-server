package com.wutsi.security.entity

import java.time.OffsetDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "T_API_KEY")
data class ApiKeyEntity(
    @Id
    public val id: String = "",
    public var name: String = "",
    public var scopes: String = "",

    @Column(name = "creation_date_time")
    public val creationDateTime: OffsetDateTime = OffsetDateTime.now(),

    @Column(name = "expiry_date_time")
    public var expiryDateTime: OffsetDateTime? = null
) {
    fun expired(): Boolean =
        expiryDateTime?.isBefore(OffsetDateTime.now()) == true
}
