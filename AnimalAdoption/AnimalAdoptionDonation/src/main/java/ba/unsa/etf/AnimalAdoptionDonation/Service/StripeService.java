package ba.unsa.etf.AnimalAdoptionDonation.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class StripeService {
    @Value("${stripe.payment.link}")
    private String stripePaymentLink;

    @Value("${stripe.success.url}")
    private String successUrl;

    public String generatePaymentLink(double amount, int donationId) {
        try {
            int amountInCents = (int) Math.round(amount * 100);
            String encodedSuccessUrl = URLEncoder.encode(
                    successUrl + "?donation_id=" + donationId,
                    StandardCharsets.UTF_8.toString()
            );

            return String.format("%s?amount=%d&client_reference_id=%d&success_url=%s",
                    stripePaymentLink,
                    amountInCents,
                    donationId,
                    encodedSuccessUrl
            );
        } catch (Exception e) {
            throw new RuntimeException("Greška pri generisanju Stripe linka", e);
        }
    }
}