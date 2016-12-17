package com.dkjar.common;

import android.content.Intent;

import java.io.Serializable;

/**
 * Intent util
 * @author dengl
 *
 */
public class Intents {

     public static final String INTENT_PREFIX = "com.dkjar.mobile.";

    /**
     * Prefix for all extra data added to intents
     */
     public static final String INTENT_EXTRA_PREFIX = INTENT_PREFIX + "extra.";

     public static final String EXTRA_REPOSITORY = INTENT_EXTRA_PREFIX  + "REPOSITORY";

     public static final String INTENT_STATUS = INTENT_PREFIX  + "STATUS";
    /**
     * User handle
     */
    public static final String EXTRA_USER = INTENT_PREFIX + "USER";

     public static final String COMPUTER = INTENT_PREFIX + "COMMENT";
    /**
     * Builder for generating an intent configured with extra data such as an
     * issue, repository, or gist
     */
    public static class Builder {

        private final Intent intent;

        public Builder(String actionSuffix) {
            intent = new Intent(INTENT_PREFIX + actionSuffix);
        }

        public Builder() {
            intent = new Intent();
        }


        public Builder status(String  status) {
            return add(INTENT_STATUS, status);
        }

        public Builder add(String fieldName, String value) {
            intent.putExtra(fieldName, value);
            return this;
        }

        public Builder add(String fieldName, CharSequence[] values) {
            intent.putExtra(fieldName, values);
            return this;
        }

        public Builder add(String fieldName, int value) {
            intent.putExtra(fieldName, value);
            return this;
        }

        public Builder add(String fieldName, int[] values) {
            intent.putExtra(fieldName, values);
            return this;
        }

        public Builder add(String fieldName, boolean[] values) {
            intent.putExtra(fieldName, values);
            return this;
        }

        public Builder add(String fieldName, Serializable value) {
            intent.putExtra(fieldName, value);
            return this;
        }

        public Builder clearTop(){
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            return this;
        }

        public Intent toIntent() {
            return intent;
        }
    }
}
