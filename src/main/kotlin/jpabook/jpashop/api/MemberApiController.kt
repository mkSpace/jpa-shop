package jpabook.jpashop.api

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Member
import jpabook.jpashop.service.MemberService
import lombok.Data
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotEmpty

@RestController
class MemberApiController(private val memberService: MemberService) {

    @GetMapping("/api/v1/members")
    fun membersV1(): List<Member> {
        return memberService.findMembers()
    }

    @GetMapping("/api/v2/members")
    fun membersV2(): Result<List<MemberDto>> {
        val findMembers = memberService.findMembers()
        return Result(
                data = findMembers.map { member ->
                    MemberDto(
                            id = member.id,
                            name = member.username,
                            address = member.address
                    )
                }.toList(),
                count = findMembers.size
        )
    }

    @Data
    data class Result<T>(val data: T, val count: Int? = null)

    @Data
    data class MemberDto(val id: Long? = null, val name: String, val address: Address? = null)

    @PostMapping("/api/v1/members")
    fun saveMemberV1(@RequestBody @Valid member: Member): CreateMemberResponse {
        val id = memberService.join(member)
        return CreateMemberResponse(id)
    }

    @PostMapping("/api/v2/members")
    fun saveMemberV2(@RequestBody @Valid request: CreateMemberRequest): CreateMemberResponse {
        val member = Member(username = request.name)
        val id = memberService.join(member)
        return CreateMemberResponse(id)
    }

    @PutMapping("/api/v2/members/{memberId}")
    fun updateMemberV2(
            @PathVariable("memberId") id: Long,
            @RequestBody @Valid request: UpdateMemberRequest
    ): UpdateMemberResponse {
        memberService.update(id, request.name)
        val findMember = memberService.findOne(id)
        return UpdateMemberResponse(id = findMember.id, name = findMember.username)
    }

    @Data
    data class CreateMemberRequest(@field:NotEmpty val name: String)

    @Data
    data class CreateMemberResponse(val id: Long)

    @Data
    data class UpdateMemberRequest(val name: String)

    @Data
    data class UpdateMemberResponse(val id: Long? = null, val name: String)
}