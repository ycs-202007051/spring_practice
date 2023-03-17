package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{
    private final MemberRepository memberRepository;

    @MainDiscountPolicy
    private final DiscountPolicy discountPolicy;


    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        //주문 생성 후 , 할인 가격 해당 멤버의 price 를 가져옴.

        //회원정보 조회, 할인 정책 적용한 다음 주문 객체를 생성해서 반환한다.
        // 메모리 회원 리포지토리와 고정 금액 할인 정책을 구현체로 생성한다.
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);


        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
