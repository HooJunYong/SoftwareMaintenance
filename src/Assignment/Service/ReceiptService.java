package Assignment.Service;

import Assignment.Model.ReceiptData;
import Assignment.View.OrderView;

public class ReceiptService {

    private OrderView orderView;

    public ReceiptService(OrderView orderView) {
        this.orderView = orderView;
    }

    public void handleReceiptGeneration(ReceiptData receiptData) {
        char confirm = orderView.promptReceiptConfirmation();
        
        if (confirm == 'Y') {
            displayReceipt(receiptData);
        } else {
            orderView.displaySuccess("Receipt skipped.");
        }
    }

    private void displayReceipt(ReceiptData data) {
        orderView.displayReceipt(data);
    }
}