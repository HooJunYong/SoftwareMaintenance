package Assignment.Model;

public class Member extends Person {

    public Member() {
        super();
    }

    public Member(String id, String name, String email, String phoneNo) {
        super(id, name, email, phoneNo);
    }

    // Validate if member ID exists in database
    public boolean validateID(String id) {
        return MemberDAO.validateMemberID(id);
    }

    @Override
    public String toString() {
        return String.format("%-10s %-20s %-30s %-15s",
                getID(), getName(), getEmail(), getPhoneNo());
    }
}