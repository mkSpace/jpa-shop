package jpabook.jpashop.domain

import lombok.Getter
import javax.persistence.Embeddable

@Embeddable
@Getter
data class Address(
        private val city: String,
        private val street: String,
        private val zipcode: String
)
