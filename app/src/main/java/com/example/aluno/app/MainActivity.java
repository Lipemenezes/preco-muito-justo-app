package com.example.aluno.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends Activity {

    private EditText editProduct;
    private EditText editValue;
    private EditText editQuantity;
    private EditText editInterest;
    private TextView viewProduct;
    private TextView viewInitialCost;
    private TextView viewInstallmentCost;
    private TextView viewTotalCost;
    private TextView viewTotalInterest;
    private CheckBox checkDownPayment;
    private DecimalFormat df = new DecimalFormat("####0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initViews();
    }

    public void calculate(View view) {
        String productName = editProduct.getText().toString();
        Double productValue = Double.parseDouble(editValue.getText().toString());
        Integer quantity = Integer.parseInt(editQuantity.getText().toString());
        Double interest = Double.parseDouble(editInterest.getText().toString());
        Boolean isDownPayment = checkDownPayment.isChecked();

        if (isDownPayment)
            interest *= 0.95;

        Double installmentConsideringInterest = getInstallmentValueWithInterest(
                productValue,
                quantity,
                interest
        );

        Double totalValue = getTotalValue(installmentConsideringInterest, quantity);
        Double totalInterest = getInterestValue(totalValue, productValue);

        setInformationToView(
                productName,
                df.format(productValue),
                df.format(installmentConsideringInterest),
                df.format(totalValue),
                df.format(totalInterest)
        );
    }

    public void setInformationToView(String productName, String initialCost, String installmentCost,
                                     String totalCost, String totalInterest) {
        viewProduct.setText(productName);
        viewInitialCost.setText(initialCost);
        viewInstallmentCost.setText(installmentCost);
        viewTotalCost.setText(totalCost);
        viewTotalInterest.setText(totalInterest);
    }

    private Double getInterestValue(Double totalValue, Double productValue) {
        return totalValue - productValue;
    }

    private Double getTotalValue(Double installmentConsideringInterest, Integer quantity) {
        return installmentConsideringInterest * quantity;
    }

    private Double getInstallmentValue(Double productValue, Integer quantity) {
        return productValue / quantity;
    }

    private Double getInstallmentValueWithInterest(Double productValue, Integer quantity,
                                                   Double interest) {
        return getInstallmentValue(productValue, quantity) * (1 + (interest / 100));

    }

    public void clear(View view) {
        editProduct.setText("");
        editValue.setText("");
        editQuantity.setText("");
        editInterest.setText("");
        viewProduct.setText("");
        viewInitialCost.setText("");
        viewInstallmentCost.setText("");
        viewTotalCost.setText("");
        viewTotalInterest.setText("");
        checkDownPayment.setChecked(false);
    }

    private void initViews() {
        editProduct = findViewById(R.id.edit_product);
        editValue = findViewById(R.id.edit_value);
        editQuantity = findViewById(R.id.edit_quantity);
        editInterest = findViewById(R.id.edit_interest);
        viewProduct = findViewById(R.id.view_product);
        viewInitialCost = findViewById(R.id.view_initial_cost);
        viewInstallmentCost = findViewById(R.id.view_installment_cost);
        viewTotalCost = findViewById(R.id.view_total_cost);
        viewTotalInterest = findViewById(R.id.view_total_interest);
        checkDownPayment = findViewById(R.id.check_down_payment);
    }

}
