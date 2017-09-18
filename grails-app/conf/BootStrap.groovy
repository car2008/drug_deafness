import java.util.UUID

import javax.script.ScriptEngine
import javax.script.ScriptEngineFactory
import javax.script.ScriptEngineManager

import grails.plugin.springsecurity.SpringSecurityUtils

import com.capitalbiotech.drugdeafness.Preference
import com.capitalbiotech.drugdeafness.Role
import com.capitalbiotech.drugdeafness.User
import com.capitalbiotech.drugdeafness.Utils;
import com.capitalbiotech.drugdeafness.UserRole;

class BootStrap {
    def springSecurityService
    def utilsService
    def grailsApplication

    def init = { servletContext ->
        //we save time always in Asia/Shanghai timezone
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"))
    
        if (0 == User.count()) {
            initializeDatabase()
        }
        
        updateDatabase()
		
		
    }
    
    def destroy = {
    
    }
    
    def updateDatabase() {
        /*def systemVersionPreference = Preference.findByKey("SYSTEM_VERSION")
        def systemVersion = systemVersionPreference["value"] as int
        
        if (systemVersion == 0) {
            //systemVersionPreference.value = 1
            //systemVersionPreference.save(flush: true)
        }*/
    }
    
    def initializeDatabase() {
        
        //preferences
        def preferenceRank = 0
        def preferences = [
            [
                type      : Preference.TYPE_SYSTEM,
                valueType : Preference.VALUE_TYPE_INTEGER,
                category  : Preference.CATEGORY_SYSTEM,
                editable  : true,
                key       : 'SYSTEM_VERSION',
                value     : '0'
            ],
            [
                type      : Preference.TYPE_SYSTEM,
                valueType : Preference.VALUE_TYPE_SINGLE_SELECTION,
                category  : Preference.CATEGORY_SYSTEM,
                editable  : true,
                key       : 'SYSTEM_MAINTENANCE',
                value     : 'disabled',
                option    : 'enabled;disabled'
            ],
            [
                type      : Preference.TYPE_SYSTEM,
                valueType : Preference.VALUE_TYPE_SINGLE_SELECTION,
                category  : Preference.CATEGORY_SYSTEM,
                editable  : true,
                key       : 'REGISTRATION_STATUS',
                value     : 'enabled',
                option    : 'enabled;disabled'
            ],
            [
                type      : Preference.TYPE_USER,
                valueType : Preference.VALUE_TYPE_SINGLE_SELECTION,
                category  : Preference.CATEGORY_GENERAL,
                editable  : true,
                key       : 'TIMEZONE',
                value     : 'Asia/Shanghai',
                option    : 'Pacific/Niue;Pacific/Pago_Pago;Pacific/Honolulu;Pacific/Rarotonga;Pacific/Tahiti;Pacific/Marquesas;America/Anchorage;Pacific/Gambier;America/Los_Angeles;America/Tijuana;America/Vancouver;America/Whitehorse;Pacific/Pitcairn;America/Dawson_Creek;America/Denver;America/Edmonton;America/Hermosillo;America/Mazatlan;America/Phoenix;America/Yellowknife;America/Belize;America/Chicago;America/Costa_Rica;America/El_Salvador;America/Guatemala;America/Managua;America/Mexico_City;America/Regina;America/Tegucigalpa;America/Winnipeg;Pacific/Easter;Pacific/Galapagos;America/Bogota;America/Cancun;America/Guayaquil;America/Havana;America/Iqaluit;America/Jamaica;America/Lima;America/Nassau;America/New_York;America/Panama;America/Port-au-Prince;America/Rio_Branco;America/Toronto;America/Asuncion;America/Barbados;America/Boa_Vista;America/Campo_Grande;America/Caracas;America/Cuiaba;America/Curacao;America/Grand_Turk;America/Guyana;America/Halifax;America/La_Paz;America/Manaus;America/Martinique;America/Port_of_Spain;America/Porto_Velho;America/Puerto_Rico;America/Santiago;America/Santo_Domingo;America/Thule;Antarctica/Palmer;Atlantic/Bermuda;America/St_Johns;America/Araguaina;America/Argentina/Buenos_Aires;America/Bahia;America/Belem;America/Cayenne;America/Fortaleza;America/Godthab;America/Maceio;America/Miquelon;America/Montevideo;America/Paramaribo;America/Recife;America/Sao_Paulo;Antarctica/Rothera;Atlantic/Stanley;America/Noronha;Atlantic/South_Georgia;America/Scoresbysund;Atlantic/Azores;Atlantic/Cape_Verde;Africa/Abidjan;Africa/Accra;Africa/Bissau;Africa/Casablanca;Africa/El_Aaiun;Africa/Monrovia;America/Danmarkshavn;Atlantic/Canary;Atlantic/Faroe;Atlantic/Reykjavik;Etc/GMT;Europe/Dublin;Europe/Lisbon;Europe/London;Africa/Algiers;Africa/Ceuta;Africa/Lagos;Africa/Ndjamena;Africa/Tunis;Africa/Windhoek;Europe/Amsterdam;Europe/Andorra;Europe/Belgrade;Europe/Berlin;Europe/Brussels;Europe/Budapest;Europe/Copenhagen;Europe/Gibraltar;Europe/Luxembourg;Europe/Madrid;Europe/Malta;Europe/Monaco;Europe/Oslo;Europe/Paris;Europe/Prague;Europe/Rome;Europe/Stockholm;Europe/Tirane;Europe/Vienna;Europe/Warsaw;Europe/Zurich;Africa/Cairo;Africa/Johannesburg;Africa/Maputo;Africa/Tripoli;Asia/Amman;Asia/Beirut;Asia/Damascus;Asia/Gaza;Asia/Jerusalem;Asia/Nicosia;Europe/Athens;Europe/Bucharest;Europe/Chisinau;Europe/Helsinki;Europe/Istanbul;Europe/Kaliningrad;Europe/Kiev;Europe/Riga;Europe/Sofia;Europe/Tallinn;Europe/Vilnius;Africa/Khartoum;Africa/Nairobi;Antarctica/Syowa;Asia/Baghdad;Asia/Qatar;Asia/Riyadh;Europe/Minsk;Europe/Moscow;Asia/Tehran;Asia/Aqtau;Asia/Baku;Asia/Dubai;Asia/Tbilisi;Asia/Yerevan;Europe/Samara;Indian/Mahe;Indian/Mauritius;Indian/Reunion;Asia/Kabul;Antarctica/Mawson;Asia/Aqtobe;Asia/Ashgabat;Asia/Dushanbe;Asia/Karachi;Asia/Tashkent;Asia/Yekaterinburg;Indian/Kerguelen;Indian/Maldives;Asia/Calcutta;Asia/Colombo;Asia/Katmandu;Antarctica/Vostok;Asia/Almaty;Asia/Bishkek;Asia/Dhaka;Asia/Omsk;Asia/Thimphu;Indian/Chagos;Asia/Rangoon;Indian/Cocos;Antarctica/Davis;Asia/Bangkok;Asia/Hovd;Asia/Jakarta;Asia/Krasnoyarsk;Asia/Saigon;Indian/Christmas;Antarctica/Casey;Asia/Brunei;Asia/Choibalsan;Asia/Hong_Kong;Asia/Irkutsk;Asia/Kuala_Lumpur;Asia/Macau;Asia/Makassar;Asia/Manila;Asia/Shanghai;Asia/Singapore;Asia/Taipei;Asia/Ulaanbaatar;Australia/Perth;Asia/Pyongyang;Asia/Dili;Asia/Jayapura;Asia/Seoul;Asia/Tokyo;Asia/Yakutsk;Pacific/Palau;Australia/Adelaide;Australia/Darwin;Antarctica/DumontDUrville;Asia/Vladivostok;Australia/Brisbane;Australia/Hobart;Australia/Sydney;Pacific/Chuuk;Pacific/Guam;Pacific/Port_Moresby;Asia/Magadan;Pacific/Efate;Pacific/Guadalcanal;Pacific/Kosrae;Pacific/Norfolk;Pacific/Noumea;Pacific/Pohnpei;Asia/Kamchatka;Pacific/Auckland;Pacific/Fiji;Pacific/Funafuti;Pacific/Kwajalein;Pacific/Majuro;Pacific/Nauru;Pacific/Tarawa;Pacific/Wake;Pacific/Wallis;Pacific/Apia;Pacific/Enderbury;Pacific/Fakaofo;Pacific/Tongatapu;Pacific/Kiritimati'
            ],
            [
                type      : Preference.TYPE_USER,
                valueType : Preference.VALUE_TYPE_SINGLE_SELECTION,
                category  : Preference.CATEGORY_GENERAL,
                editable  : true,
                key       : 'THEME',
                value     : 'default',
                option    : 'default;highcontrast;sepia;night'
            ],
            [
                type      : Preference.TYPE_USER,
                valueType : Preference.VALUE_TYPE_MULTIPLE_SELECTION,
                category  : Preference.CATEGORY_NOTIFICATION,
                editable  : true,
                key       : 'NOTIFICATION_EMAIL',
                value     : 'run;member;comment',
                option    : 'run;member;comment'
            ],
        ]
        
        def preference = null
        preferences.each {preferenceProperties ->
            preferenceProperties['rank'] = preferenceRank++
            preference = new Preference(preferenceProperties).save(flush: true)
        }
        
        //users and roles
        def userRole  = new Role(authority:'ROLE_USER').save(flush: true)
        def staffRole = new Role(authority:'ROLE_STAFF').save(flush: true)
        def adminRole = new Role(authority:'ROLE_ADMIN').save(flush: true)
        
        def admin = new User(
            username: 'bioinfo@capitalbiotech.com',
            password: '123456',
            name: 'CBT Bioinformatics'
        ).save(flush: true)
        UserRole.create(admin, userRole, true)
        UserRole.create(admin, staffRole, true)
        UserRole.create(admin, adminRole, true)
        
        def user1 = new User(
            username: 'leiwang@capitalbiotech.com',
            password: '123456',
            name: 'Wang Lei'
        ).save(flush: true)
        UserRole.create(user1, userRole, true)
		UserRole.create(user1, staffRole, true)
		UserRole.create(user1, adminRole, true)
    }
	
	def readRawFile(fileName) {
		def rawLines = new File(BootStrap.class.getResource("raw/${fileName}").getFile()).readLines()
		if (rawLines.size() < 2) {
			return
		}
		
		def propertiesList = []
		def keys = rawLines.get(0).split("\t")
		def values = null
		def line = null
		for (int i = 1; i < rawLines.size(); i++) {
			def properties = [:]
			
			line = rawLines.get(i)
			if (Utils.isEmpty(line)) {
				continue
			}
			
			values = line.split("\t")
			for (int j = 0; j < values.size(); j++) {
				properties[(keys[j])] = values[j]
			}
			
			propertiesList << properties
		}
		
		return propertiesList
	}
	
}
