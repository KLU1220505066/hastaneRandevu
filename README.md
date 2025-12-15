
🏥 Hastane Randevu Sistemi

Bu proje, Java Swing kullanılarak geliştirilmiş ve MySQL veritabanı ile çalışan masaüstü tabanlı bir Hastane Randevu Yönetim Sistemidir.
Sistem; Hasta ve Doktor kullanıcı rollerini destekler ve randevu alma, yönetme ve muayene süreçlerini kapsar.

📌 Proje Özellikleri
Genişlet
message.txt
4 KB
﻿
🏥 Hastane Randevu Sistemi

Bu proje, Java Swing kullanılarak geliştirilmiş ve MySQL veritabanı ile çalışan masaüstü tabanlı bir Hastane Randevu Yönetim Sistemidir.
Sistem; Hasta ve Doktor kullanıcı rollerini destekler ve randevu alma, yönetme ve muayene süreçlerini kapsar.

📌 Proje Özellikleri
👤 Kullanıcı Rolleri

Hasta

Doktor

🧑‍⚕️ Hasta Paneli

Kayıt olma ve giriş yapma

Branşa göre doktor listeleme

Doktorun çalışma saatlerine göre randevu alma

Aynı gün içinde yalnızca 1 randevu alma kuralı

Randevu iptal etme

Randevu tarih ve saat güncelleme

Randevu geçmişini görüntüleme

Doktor arama (Ad, Soyad, Branş)

Profil bilgilerini güncelleme (iletişim, şifre)

👨‍⚕️ Doktor Paneli

Günlük / haftalık / tarih aralığına göre randevu listeleme

Randevu durumlarını güncelleme:

AKTIF

TAMAMLANDI

GELMEDI

IPTAL

Muayene notu ve reçete girme

Hasta arama (TC / Ad Soyad)

Seçilen hastanın randevu geçmişini görüntüleme

Çalışma saatlerini belirleme

Profil bilgilerini güncelleme

🧠 Kullanılan Tasarım Desenleri
✅ Zorunlu Tasarım Desenleri

Factory Pattern

UserFactory

State Pattern

AppointmentState

AktifState, IptalState, TamamlandiState, GelmediState

Observer Pattern

AppointmentObserver

AppointmentSubject

Abstract Class

User

BaseDashboard

➕ Ek Tasarım Desenleri

Template Method Pattern

AbstractViewTemplate

Strategy Pattern

WorkingHourStrategy

HourlyWorkingHourStrategy

🛠 Kullanılan Teknolojiler

Java (JDK 17+)

Java Swing (GUI)

MySQL

JDBC

LocalDate / LocalTime API

🗄 Veritabanı Yapısı
📄 Tablolar

users

patients

doctors

appointments

📌 İş Kuralları

Aynı doktor, aynı gün ve aynı saat için birden fazla randevu alınamaz

Hasta aynı gün içinde birden fazla randevu alamaz

Doktor çalışma saatleri saatlik slotlara bölünür

Örnek çalışma saati formatı:
09:00-12:00,13:00-17:00

⚙️ Kurulum
1️⃣ Veritabanını Oluştur

CREATE DATABASE hospital_randevu;

Tabloların oluşturulması için uygun SQL scriptlerinin çalıştırılması gerekir.

2️⃣ Veritabanı Bağlantı Ayarları

DatabaseManager sınıfı içinde kendi MySQL bilgilerinizi girin:

URL: jdbc:mysql://localhost:3306/hospital_randevu

USER: root

PASS: 1234

3️⃣ Uygulamayı Çalıştır

javac HastaneSistemi.java
java HastaneSistemi

veya IDE üzerinden main metodunu çalıştırabilirsiniz.

▶️ Uygulama Başlangıç Noktası

HastaneSistemi sınıfı içindeki main metodu uygulamanın giriş noktasıdır.

🔐 Güvenlik Notu

Şifreler eğitim amacıyla düz metin olarak saklanmaktadır

Gerçek sistemlerde şifrelerin hashlenmesi önerilir (BCrypt vb.)

🚀 Geliştirilebilir Özellikler

Şifre hashleme

Bildirim sistemi

PDF reçete çıktısı

Web veya mobil arayüz

REST API entegrasyonu

👨‍💻 Proje Bilgisi

Proje Türü: Akademik / Eğitim Amaçlı

Programlama Dili: Java

Arayüz: Java Swing

📄 Lisans

![class_user_patient_doctor png](https://github.com/user-attachments/assets/2eb7a1c6-3671-4c58-99df-608fc715f069)

![sequence_hasta_randevu png](https://github.com/user-attachments/assets/7ce62b03-dc91-413e-97d8-06463e1167bf)

![er_hastane_randevu png](https://github.com/user-attachments/assets/69d52919-d856-43f9-953d-b2a5ae5db22f)

![use_case_hastane_randevu png](https://github.com/user-attachments/assets/e8aaec1b-f7e5-4c01-898d-ff210feb3bc9)

![class_abstracts_and_patterns png](https://github.com/user-attachments/assets/94e93cce-2590-4c29-b2a8-d292c9540034)











Bu proje eğitim amaçlıdır ve serbestçe geliştirilebilir.

Alp Erin Şenel 1220505066 -- https://github.com/KLU1220505066/hastaneRandevu/tree/main

Kerem Yalın Taşkın 5210505028 --  https://github.com/keremyalintaskin/hastahane_randevu_sistemi

Arda Işık 5210505028 -- https://github.com/5210505058/hastane_randevu_sistemi






