package Assignment.Service;

import Assignment.Model.Cart;
import Assignment.Model.Member;
import Assignment.Model.MemberDAO;
import Assignment.Model.Payment;
import Assignment.Config.AppConfig;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@Tag("integration")
public class MemberServiceIntegrationTest {

    private MemberService memberService;
    private Payment payment;
    private Cart cart;
    private Member testMember;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        payment = new Payment(cart);
        
        // Get member from real database
        testMember = MemberDAO.findById("M001");
        
        // Skip test if database is not available or member doesn't exist
        assumeTrue(testMember != null, "Database not available or M001 not found");
    }

    // ========== createNonMember Integration Tests ==========

    @Test
    @DisplayName("Integration: createNonMember returns Walk-in Customer")
    void testCreateNonMemberReturnsWalkInCustomer() {
        // Arrange
        MockOrderView mockOrderView = new MockOrderView();
        memberService = new MemberService(mockOrderView, new MemberDAO());

        // Act
        Member result = memberService.createNonMember(payment);

        // Assert
        assertNotNull(result);
        assertEquals("-", result.getID());
        assertEquals("Walk-in Customer", result.getName());
    }

    @Test
    @DisplayName("Integration: createNonMember sets zero discount")
    void testCreateNonMemberSetsZeroDiscount() {
        // Arrange
        MockOrderView mockOrderView = new MockOrderView();
        memberService = new MemberService(mockOrderView, new MemberDAO());

        // Act
        memberService.createNonMember(payment);

        // Assert
        assertEquals(0.00, payment.getDiscount(), 0.001);
    }

    // ========== validateMember Integration Tests ==========

    @Test
    @DisplayName("Integration: validateMember with valid ID from database")
    void testValidateMemberWithValidId() {
        // Arrange
        MockOrderView mockOrderView = new MockOrderView();
        memberService = new MemberService(mockOrderView, new MemberDAO());

        // Act
        Member result = memberService.validateMember(payment, "M001");

        // Assert
        assertNotNull(result, "Member M001 should exist in database");
        assertEquals("M001", result.getID());
        assertNotNull(result.getName());
    }

    @Test
    @DisplayName("Integration: validateMember sets member discount")
    void testValidateMemberSetsMemberDiscount() {
        // Arrange
        MockOrderView mockOrderView = new MockOrderView();
        memberService = new MemberService(mockOrderView, new MemberDAO());

        // Act
        memberService.validateMember(payment, "M001");

        // Assert
        assertEquals(AppConfig.getMemberDiscount(), payment.getDiscount(), 0.001);
    }

    @Test
    @DisplayName("Integration: validateMember with invalid ID returns null")
    void testValidateMemberWithInvalidId() {
        // Arrange
        MockOrderView mockOrderView = new MockOrderView();
        memberService = new MemberService(mockOrderView, new MemberDAO());

        // Act
        Member result = memberService.validateMember(payment, "INVALID_ID");

        // Assert
        assertNull(result, "Invalid member ID should return null");
    }

    // ========== handleMemberLogin Integration Tests ==========

    @Test
    @DisplayName("Integration: handleMemberLogin as non-member")
    void testHandleMemberLoginAsNonMember() {
        // Arrange
        MockOrderView mockOrderView = new MockOrderView();
        mockOrderView.setMemberTypeResponse(2);  // Select "Non-Member"
        memberService = new MemberService(mockOrderView, new MemberDAO());

        // Act
        Member result = memberService.handleMemberLogin(payment);

        // Assert
        assertNotNull(result);
        assertEquals("-", result.getID());
        assertEquals("Walk-in Customer", result.getName());
    }

    @Test
    @DisplayName("Integration: handleMemberLogin as valid member from database")
    void testHandleMemberLoginAsValidMember() {
        // Arrange
        MockOrderView mockOrderView = new MockOrderView();
        mockOrderView.setMemberTypeResponse(1);  // Select "Member"
        mockOrderView.setMemberIdResponse("M001");
        memberService = new MemberService(mockOrderView, new MemberDAO());

        // Act
        Member result = memberService.handleMemberLogin(payment);

        // Assert
        assertNotNull(result, "Member should be found in database");
        assertEquals("M001", result.getID());
    }

    @Test
    @DisplayName("Integration: handleMemberLogin with invalid member ID shows error")
    void testHandleMemberLoginWithInvalidMemberId() {
        // Arrange
        MockOrderView mockOrderView = new MockOrderView();
        mockOrderView.setMemberTypeResponse(1);  // Select "Member"
        mockOrderView.setMemberIdResponse("INVALID");
        mockOrderView.setMemberIdResponseSecond("M001"); // Valid ID on retry
        memberService = new MemberService(mockOrderView, new MemberDAO());

        // Act
        Member result = memberService.handleMemberLogin(payment);

        // Assert
        // Verify error was displayed for invalid ID
        assertTrue(mockOrderView.wasErrorDisplayed());
    }

    // ========== Debug Test ==========

    @Test
    @DisplayName("Debug: Check database connection for members")
    void testDatabaseConnection() {
        Member member = MemberDAO.findById("M001");
        
        if (member == null) {
            System.out.println("ERROR: Member M001 not found!");
            System.out.println("Check: 1) Database running? 2) Member exists? 3) Connection settings?");
        } else {
            System.out.println("SUCCESS: Found member - " + member.getName());
            System.out.println("Member ID: " + member.getID());
        }
        
        assertNotNull(member, "Member M001 should exist in database");
    }
}