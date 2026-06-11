package QRDriver.Core;
/**
 * Copyright © 2019-2021 The CETC PHM Authors.
 *
 */
import java.util.List;

/**
 * Class for generating QR code data.
 */
public class _DataBuilder {
    
    /*
     * URL
     */
    private static final String MAILTO = "mailto";
    private static final String SUBJECT = "subject";
    /*
     * PHONE
     */
    private static final String TEL = "tel";
    /*
     * SMS/MMS
     */
    private static final String SMS = "sms";
    private static final String MMS = "mms";
    /*
     * GEO
     */
    private static final String GEO = "geo";
    /*
     * WIFI
     */
    private static final String WIFI = "WIFI";
    /*
     * Visit Card
     */
    private static final String BEGIN_VCARD = "BEGIN:VCARD";
    private static final String NEW_LINE = "\r\n";
    
    /*
     * Code data
     */
    private String data;
    /*
     * Code data type
     */
    DataType type;
    
    /**
     * Types of authentication for Wi-Fi network.
     */
    public enum Authentication {
        /**
         * Protocol WEP.
         */
        WEP("WEP"),

        /**
         * Protocol WPA.
         */
        WPA("WPA"),

        /**
         * Protocol WPA2-EAP.
         */
        WPA2EAP("WPA2-EAP"),

        /**
         * Passwordless access.
         */
        NOPASS("nopass");
        
        private Authentication(String val) {
            this.value = val;
        }

        /**
         * Get value.
         * @return value
         */
        public String getValue() {
            return value;
        }
        private final String value;
    }

    /**
     * Class constructor no data.
     * @param type data type for code
     */
    public _DataBuilder(DataType type) {
        this.type = type;
    }
    
    /**
     * Class constructor with data.
     * @param type data type for code
     * @param data code data
     */
    public _DataBuilder(DataType type, String data) {
        this.type = type;
        this.data = data;
    }
    
    /**
     * Get data.
     * @return current code data
     */
    public String getData(){
        return this.data;
    }
    
    /**
     * Set data.
     * @param data data for code
     */
    public void setData(String data){
        this.data = data;
    }
    
    /**
     * Create URL code.
     * @param address url address
     */
    public void byURL(String address){
        this.data = address.trim();
    }
    
    /**
     * Create EMAIL code.
     * @param address email address
     * @param subject email subject
     */
    public void byEMAIL(String address, String subject){
        this.data = MAILTO + ":" + address.toLowerCase().trim() + (subject != null ? "?" + SUBJECT + "=" + subject.trim() : "");
    }
    
    /**
     * Create PHONE code (format - tel:+375-452-893-3445).
     * @param number phone number
     */
    public void byPHONE(String number){
        this.data = TEL + ":" + number.trim();
    }

    /**
     * Create SMS code (format - sms:+375-452-893-3445:subject).
     * @param number phone number
     * @param msg message text
     */
    public void bySMS(String number, String msg){
        this.data = SMS + ":" + number.trim() + (msg != null ? ":" + msg.trim() : "");
    }

    /**
     * Create MMS code (format - mms:+3-452-893-3445:subject).
     * @param number phone number
     * @param msg message text
     */
    public void byMMS(String number, String msg){
        this.data = MMS + ":" + number.trim() + (msg != null ? ":" + msg.trim() : "");
    }
    
    /**
     * Create SMS code for multiple recipients (format - sms:+3-452-893-3445,+375-452-893-344,+375-342-802-121).
     * @param numbers phone numbers
     */
    public void bySMS(List<String> numbers){
        this.data = SMS + ":";
        for(int i=0;i<numbers.size();i++){
            //this.data.concat(numbers.get(i).trim()+",");
            if(i<numbers.size()-1){
                this.data.concat(",");
            }
        }
        //if (this.data.endsWith(",")){
        //    this.data = this.data.substring(0, this.data.length() - 1);
        //}
    }

    /**
     * Create GEO code (format - geo:12.3476,67.9022).
     * @param latitude coordinate latitude
     * @param longitude coordinate longitude
     */
    public void byGEO(String latitude, String longitude){
        this.data = GEO + ":" + latitude + "," + longitude;
    }
    
    /**
     * Create WIFI code (format - WIFI:T:AUTHENTICATION;S:SSID;P:PSK;H:HIDDEN;).
     * @param authentication types of authentication protocol
     * @param ssid network SSID
     * @param pass network password
     */
    public void byWIFI(String authentication, String ssid, String pass){
        this.data = WIFI + ":" + 
                    "T:" + Authentication.valueOf(authentication).getValue() + ";" +
                    "S" + ssid.toLowerCase().trim() + ";" +
                    "P:" + pass.toLowerCase().trim() + ";;";
    }

    /**
     * Create vCard code (format -
     * BEGIN:VCARD
     * VERSION:3.0
     * N:Lastname;Surname
     * FN:Displayname
     * ORG:Testation
     * URL:http://www.test.com/
     * EMAIL:info@test.com
     * TEL;TYPE=voice,work,pref:+375 2534 56788
     * ADR;TYPE=intl,work,postal,parcel:;;Teststr. 1;Minsk;;12345;Belarus
     * END:VCARD).
     * @param firstName first name
     * @param lastName last name
     * @param title title info
     * @param organisation organisation
     * @param email email address
     * @param phone phone number
     */
    public void byVCARD(String firstName, String lastName, String title, String organisation, String email, String phone){
        this.data = BEGIN_VCARD + NEW_LINE +
                    "VERSION:3.0" + NEW_LINE +
                    "N:" + lastName.trim() + ";" + firstName.trim() + NEW_LINE +
                    "FN:" + firstName.trim() + " " + lastName.trim() + NEW_LINE +
                    "TITLE:" + title.trim() + NEW_LINE +
                    "ORG:" + organisation.trim() + NEW_LINE +
                    "EMAIL:" + email.trim() + NEW_LINE +
                    "TEL;TYPE=\"work,voice\";PREF:" + phone.trim() + NEW_LINE +
                    "END:VCARD";
    }
    
    /**
     * Checking code data.
     * @return true - OK, false - incorrect data
     */
    public boolean checkData(){
        switch(this.type){
        // check URL
        case URL:
            if (this.data == null || 
                (!this.data.trim().toLowerCase().startsWith("http") && !this.data.trim().toLowerCase().startsWith("https"))) {
                return false;
            }
            break;
        // check EMAIL
        case EMAIL:
            if (this.data == null || !this.data.toLowerCase().startsWith(MAILTO)) {
                return false;
            }
            break;
        // check PHONE
        case PHONE:
            if (this.data == null || !this.data.trim().toLowerCase().startsWith(TEL)) {
                return false;
            }
            break;
        // check SMS
        case SMS:
            if (this.data == null || !this.data.trim().toLowerCase().startsWith(SMS)) {
                return false;
            }
            break;
        // check GEO
        case GEO:
            if (this.data == null || !this.data.trim().toLowerCase().startsWith(GEO)) {
                return false;
            }
            break;
        // check WIFI
        case WIFI:
            if (this.data == null || !this.data.startsWith(WIFI)) {
                return false;
            }
            break;
        // check vCard
        case VCARD:
            if (this.data == null || !this.data.startsWith(BEGIN_VCARD)) {
                return false;
            }
            break;
        default: return false;
        }
        
        return true;
    }
    
}
