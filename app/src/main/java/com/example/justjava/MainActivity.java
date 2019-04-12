package com.example.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }




    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //Find the user's name
        EditText customerName = findViewById(R.id.nameField);
        String name = String.valueOf(customerName.getText());

        //Figure out if the user wants whipped cream
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkBox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //Figure out if the user wants chocolate
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkBox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        //Calculate the order price
        int price = calculatePrice(hasWhippedCream, hasChocolate);


        String priceMessage = createOrderSummary(price, name, hasWhippedCream, hasChocolate);
        //displayMessage(priceMessage);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void increment(View view) {
        if(quantity == 100) {
            Context context = getApplicationContext();
            CharSequence text = "You can only order 100 coffees!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;

        }
        quantity++;
        display(quantity);
    }

    public void decrement(View view) {
        if(quantity == 1) {
            Context context = getApplicationContext();
            CharSequence text = "You can't order less than 1 coffee!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        quantity--;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryText = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryText.setText(message);
    }

    /**
     * Calculates the price of the order based on the extras and current quantity.
     * @param addWhippedCream is whether whipped cream was added
     * @param addChocolate is whether chocolate was addded
     * @return the price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int price = 5;

        if(addWhippedCream) {
            price += 1;
        }

        if(addChocolate) {
            price += 2;
        }

        return price * quantity;
    }

    /**
     * Create summary of the order.
     *
     * @param price of the order
     * @param name of the customer
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @return text summary
     */
    public String createOrderSummary(int price, String name, boolean addWhippedCream, boolean addChocolate) {
        //throwing an error, don't know why
        //String orderSummary = getString(R.string.order_summary_name, name); //uses the string resource and fills in the blank with the name variable
        String orderSummary = getString(R.string.order_summary_name);
        orderSummary += "\nAdd whipped cream? " + addWhippedCream;
        orderSummary += "\nAdd chocolate? " + addChocolate;
        orderSummary += "\nQuantity: " + quantity;
        //throwing an error, don't know why
        //orderSummary += "\n" + getString(R.string.order_summary_price,
                //NumberFormat.getCurrencyInstance().format(price));

        orderSummary += "\n" + getString(R.string.thank_you);
        return orderSummary;
    }

}
