package Assignment.Service;

import Assignment.Config.AppConfig;
import Assignment.Model.Member;
import Assignment.Model.MemberDAO;
import Assignment.Model.Payment;
import Assignment.View.OrderView;


public class MemberService implements MemberServiceInterface {

    private OrderView orderView;

    public MemberService(OrderView orderView) {
        this.orderView = orderView;
    }

    @Override
    public Member handleMemberLogin(Payment payment) {
        while (true) {
            int type = orderView.promptMemberType();

            if (type == 2) {
                return createNonMember(payment);
            } else if (type == 1) {
                Member member = validateMember(payment);
                if (member != null) {
                    return member;
                }
            } else {
                orderView.displayError("Invalid selection. Please enter 1 or 2.");
            }
        }
    }

    private Member createNonMember(Payment payment) {
        Member nonMember = new Member();
        nonMember.setID("-");
        nonMember.setName("Walk-in Customer");
        payment.setDiscount(0.00);
        return nonMember;
    }

    private Member validateMember(Payment payment) {
        String id = orderView.promptMemberID();
        Member member = MemberDAO.findById(id);

        if (member != null) {
            payment.setDiscount(AppConfig.getMemberDiscount());
            return member;
        } else {
            orderView.displayError("Invalid Member ID. Please try again.");
            return null;
        }
    }
}