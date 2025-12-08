package Assignment.Service;

import Assignment.View.OrderView;

public class MockOrderView extends OrderView {

    private int memberTypeResponse;
    private String memberIdResponse;
    private String memberIdResponseSecond;
    private boolean useSecondResponse = false;
    private boolean errorDisplayed = false;

    public MockOrderView() {
        super();
    }

    public void setMemberTypeResponse(int response) {
        this.memberTypeResponse = response;
    }

    public void setMemberIdResponse(String response) {
        this.memberIdResponse = response;
    }

    public void setMemberIdResponseSecond(String response) {
        this.memberIdResponseSecond = response;
    }

    @Override
    public int promptMemberType() {
        return memberTypeResponse;
    }

    @Override
    public String promptMemberID() {
        if (useSecondResponse && memberIdResponseSecond != null) {
            return memberIdResponseSecond;
        }
        useSecondResponse = true;
        return memberIdResponse;
    }

    @Override
    public void displayError(String msg) {
        errorDisplayed = true;
        System.out.println("[Mock Error]: " + msg);
    }

    @Override
    public void displaySuccess(String msg) {
        System.out.println("[Mock Success]: " + msg);
    }

    public boolean wasErrorDisplayed() {
        return errorDisplayed;
    }
}