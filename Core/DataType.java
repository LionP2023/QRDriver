package QRDriver.Core;
/**
 * Copyright © 2019-2021 The CETC PHM Authors.
 *
 */
/**
 * Supported Data Types for QR code.
 */
public enum DataType {
    /** URL address.
     */
    URL,

    /** Email address.
     */
    EMAIL,

    /** Phone number.
     */
    PHONE,

    /** SMS message.
     */
    SMS,

    /** MMS message.
     */
    MMS,

    /** Geographical coordinates.
     */
    GEO,

    /** WIFI connection.
     */
    WIFI,

    /** Visiting card.
     */
    VCARD,

    /** Any text data.
     */
    TEXT
}
