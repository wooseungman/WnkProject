package co.wnk.framework.core.security.vo;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class User implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    private List<Role> authorities;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    
    private String id;
    private String passwd;
    private String name;
    private String member_type_code;
    private String first_class_code;
    private String member_class_code;
    private String join_type_code;
    private String birth_day;
    private String birth_type_code;
    private String gender;
    private String personal_email;
    private String phone;
    private String mobile;
    private String zipcode;
    private String basic_address;
    private String detail_address;
    private String bank_code;
    private String account_number;
    private String email_yn;
    private String sms_yn;
    private String ipin_number;
    private String ipin_ssn_hash;
    private String join_date;
    private String join_ip;
    private String mod_date;
    private String mod_ip;
    private String login_count;
    private String latest_login_date;
    private String latest_login_ip;
    private String first_order_yn;
    private String first_order_date;
    private String point;
    private String secede_yn;
    private String secede_type_code;
    private String secede_detail;
    private String secede_id;
    private String secede_date;
    private String secede_ip;
    private String broker_bgmall_code;
    private String group_id;
    private String language_code;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(List<Role> authorities) {
        this.authorities = authorities;
    }

    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMember_type_code() {
		return member_type_code;
	}

	public void setMember_type_code(String member_type_code) {
		this.member_type_code = member_type_code;
	}

	public String getFirst_class_code() {
		return first_class_code;
	}

	public void setFirst_class_code(String first_class_code) {
		this.first_class_code = first_class_code;
	}

	public String getMember_class_code() {
		return member_class_code;
	}

	public void setMember_class_code(String member_class_code) {
		this.member_class_code = member_class_code;
	}

	public String getJoin_type_code() {
		return join_type_code;
	}

	public void setJoin_type_code(String join_type_code) {
		this.join_type_code = join_type_code;
	}

	public String getBirth_day() {
		return birth_day;
	}

	public void setBirth_day(String birth_day) {
		this.birth_day = birth_day;
	}

	public String getBirth_type_code() {
		return birth_type_code;
	}

	public void setBirth_type_code(String birth_type_code) {
		this.birth_type_code = birth_type_code;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPersonal_email() {
		return personal_email;
	}

	public void setPersonal_email(String personal_email) {
		this.personal_email = personal_email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getBasic_address() {
		return basic_address;
	}

	public void setBasic_address(String basic_address) {
		this.basic_address = basic_address;
	}

	public String getDetail_address() {
		return detail_address;
	}

	public void setDetail_address(String detail_address) {
		this.detail_address = detail_address;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getAccount_number() {
		return account_number;
	}

	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}

	public String getEmail_yn() {
		return email_yn;
	}

	public void setEmail_yn(String email_yn) {
		this.email_yn = email_yn;
	}

	public String getSms_yn() {
		return sms_yn;
	}

	public void setSms_yn(String sms_yn) {
		this.sms_yn = sms_yn;
	}

	public String getIpin_number() {
		return ipin_number;
	}

	public void setIpin_number(String ipin_number) {
		this.ipin_number = ipin_number;
	}

	public String getIpin_ssn_hash() {
		return ipin_ssn_hash;
	}

	public void setIpin_ssn_hash(String ipin_ssn_hash) {
		this.ipin_ssn_hash = ipin_ssn_hash;
	}

	public String getJoin_date() {
		return join_date;
	}

	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}

	public String getJoin_ip() {
		return join_ip;
	}

	public void setJoin_ip(String join_ip) {
		this.join_ip = join_ip;
	}

	public String getMod_date() {
		return mod_date;
	}

	public void setMod_date(String mod_date) {
		this.mod_date = mod_date;
	}

	public String getMod_ip() {
		return mod_ip;
	}

	public void setMod_ip(String mod_ip) {
		this.mod_ip = mod_ip;
	}

	public String getLogin_count() {
		return login_count;
	}

	public void setLogin_count(String login_count) {
		this.login_count = login_count;
	}

	public String getLatest_login_date() {
		return latest_login_date;
	}

	public void setLatest_login_date(String latest_login_date) {
		this.latest_login_date = latest_login_date;
	}

	public String getLatest_login_ip() {
		return latest_login_ip;
	}

	public void setLatest_login_ip(String latest_login_ip) {
		this.latest_login_ip = latest_login_ip;
	}

	public String getFirst_order_yn() {
		return first_order_yn;
	}

	public void setFirst_order_yn(String first_order_yn) {
		this.first_order_yn = first_order_yn;
	}

	public String getFirst_order_date() {
		return first_order_date;
	}

	public void setFirst_order_date(String first_order_date) {
		this.first_order_date = first_order_date;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getSecede_yn() {
		return secede_yn;
	}

	public void setSecede_yn(String secede_yn) {
		this.secede_yn = secede_yn;
	}

	public String getSecede_type_code() {
		return secede_type_code;
	}

	public void setSecede_type_code(String secede_type_code) {
		this.secede_type_code = secede_type_code;
	}

	public String getSecede_detail() {
		return secede_detail;
	}

	public void setSecede_detail(String secede_detail) {
		this.secede_detail = secede_detail;
	}

	public String getSecede_id() {
		return secede_id;
	}

	public void setSecede_id(String secede_id) {
		this.secede_id = secede_id;
	}

	public String getSecede_date() {
		return secede_date;
	}

	public void setSecede_date(String secede_date) {
		this.secede_date = secede_date;
	}

	public String getSecede_ip() {
		return secede_ip;
	}

	public void setSecede_ip(String secede_ip) {
		this.secede_ip = secede_ip;
	}

	public String getBroker_bgmall_code() {
		return broker_bgmall_code;
	}

	public void setBroker_bgmall_code(String broker_bgmall_code) {
		this.broker_bgmall_code = broker_bgmall_code;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLanguage_code() {
		return language_code;
	}

	public void setLanguage_code(String language_code) {
		this.language_code = language_code;
	}
}
