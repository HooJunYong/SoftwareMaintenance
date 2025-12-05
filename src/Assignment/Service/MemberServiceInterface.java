package Assignment.Service;

import Assignment.Model.Member;
import Assignment.Model.Payment;

public interface MemberServiceInterface {
    Member handleMemberLogin(Payment payment);
}