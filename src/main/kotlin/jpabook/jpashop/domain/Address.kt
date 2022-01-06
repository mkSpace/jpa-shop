package jpabook.jpashop.domain

import javax.persistence.Embeddable

@Embeddable
class Address(
        var city: String? = null,
        var street: String? = null,
        var zipcode: String? = null
)
