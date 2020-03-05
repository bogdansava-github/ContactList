USE ContactList;
SELECT
u.user_id,
u.first_name,
u.last_name,
u.email,
u.age,
ph_m.country_code,
ph_m.phone_number,
ph_h.country_code,
ph_h.phone_number,
ph_w.country_code,
ph_w.phone_number,
ad_h.street_name,
ad_h.street_no,
ad_h.apt_no,
ad_h.apt_floor,
ad_h.zip_code,
ad_h.city,
ad_h.country,
cp.job_title,
cp.company_name,
ad_w.street_name,
ad_w.street_no,
ad_w.apt_no,
ad_w.apt_floor,
ad_w.zip_code,
ad_w.city,
ad_w.country,
u.is_favourite

FROM users u
INNER JOIN
companies cp
ON u.user_id=cp.user_id
INNER JOIN
addresses ad_h
ON u.user_id=ad_h.user_id AND ad_h.address_cat='h'
INNER JOIN
addresses ad_w
ON u.user_id=ad_w.user_id AND ad_w.address_cat='w'
INNER JOIN
phonenumbers ph_m
ON u.user_id=ph_m.user_id AND ph_m.phone_cat='m'
INNER JOIN
phonenumbers ph_h
ON u.user_id=ph_h.user_id AND ph_h.phone_cat='h'
INNER JOIN
phonenumbers ph_w
ON u.user_id=ph_w.user_id AND ph_w.phone_cat='w';