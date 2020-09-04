package com.gastonlagaf.xero.client.stripe.util;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class ChargeErrorMapping {

    private final Map<String, String> map = Map.ofEntries(
            Map.entry("authentication_required", ""),
            Map.entry("approve_with_id", ""),
            Map.entry("call_issuer", ""),
            Map.entry("card_not_supported", "Your card is not supported for payment"),
            Map.entry("card_velocity_exceeded", ""),
            Map.entry("currency_not_supported", ""),
            Map.entry("do_not_honor", ""),
            Map.entry("do_not_try_again", ""),
            Map.entry("duplicate_transaction", ""),
            Map.entry("expired_card", "Your card has been expired"),
            Map.entry("fraudulent", ""),
            Map.entry("generic_decline", ""),
            Map.entry("incorrect_number", ""),
            Map.entry("incorrect_cvc", "Incorrect cvc code passed"),
            Map.entry("incorrect_pin", "Incorrect pin code passed"),
            Map.entry("incorrect_zip", "Incorrect zip code passed"),
            Map.entry("insufficient_funds", "Insufficient funds"),
            Map.entry("invalid_account", ""),
            Map.entry("invalid_amount", ""),
            Map.entry("invalid_cvc", "Invalid cvc code passed"),
            Map.entry("invalid_expiry_year", "Invalid expiry year specified"),
            Map.entry("invalid_number", "Invalid zip code passed"),
            Map.entry("invalid_pin", "Invalid pin code passed"),
            Map.entry("issuer_not_available", ""),
            Map.entry("lost_card", ""),
            Map.entry("merchant_blacklist", ""),
            Map.entry("new_account_information_available", ""),
            Map.entry("no_action_taken", ""),
            Map.entry("not_permitted", ""),
            Map.entry("offline_pin_required", ""),
            Map.entry("online_or_offline_pin_required", ""),
            Map.entry("pickup_card", ""),
            Map.entry("pin_try_exceeded", ""),
            Map.entry("processing_error", ""),
            Map.entry("reenter_transaction", ""),
            Map.entry("restricted_card", ""),
            Map.entry("revocation_of_all_authorizations", ""),
            Map.entry("revocation_of_authorization", ""),
            Map.entry("security_violation", ""),
            Map.entry("service_not_allowed", ""),
            Map.entry("stolen_card", ""),
            Map.entry("stop_payment_order", ""),
            Map.entry("testmode_decline", ""),
            Map.entry("transaction_not_allowed", ""),
            Map.entry("try_again_later", ""),
            Map.entry("withdrawal_count_limit_exceeded", "")
    );

    public String get(String key) {
        return get(key, null);
    }

    public String get(String key, String defaultValue) {
        return map.getOrDefault(key, null != defaultValue ? defaultValue : "Unknown payment error");
    }

}
