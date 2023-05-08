package com.web.project.entity;

public enum OrderStatus {
    NEW {
        @Override
        public String defaultDescription() {
            return "Order was placed by the customer";
        }

    },
    PICKED {
        @Override
        public String defaultDescription() {
            return "Order was picked by the shipper";
        }

    },
    SHIPPING {
        @Override
        public String defaultDescription() {
            return "Order was shipping by the shipper";
        }

    },
    DELIVERED {
        @Override
        public String defaultDescription() {
            return "Order was delivered by the shipper";
        }

    },
    PAID {
        @Override
        public String defaultDescription() {
            return "Order was paid by the customer";
        }

    },
    RETURNED {
        @Override
        public String defaultDescription() {
            return "Order was returned by the customer";
        }

    };
    public abstract String defaultDescription();
}
