package com.trinity.ms.invoicing.sale_session.share;

public enum SalesSessionStatus {
    CANCELLED( new Short("4")),
    CLOSED(new Short("2")),
    OPENED(new Short("1")),
    STAND_BY(new Short("3"));

    private Short id;

    SalesSessionStatus(Short id) {
        this.id = id;

    }

    public Short getValue() {
        switch (id) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
        }
        return -1;
    }


}
