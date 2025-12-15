memoss7517
memoss7517
Bir Aramada

houdini
, 
STABEL
 kullanıcısını gruba ekledi. — 17:39
houdini
 bir arama başlattı. — 17:39
STABEL — 19:51
hasta almış olduğu randevuyu iptal edemiyor
hasta branj ve o branşta farklı doktor seçebilsin
hasta almış randevu uygun saati güncelleyemiyor 
doktor sistem üzerinden günlük ve haftalık olacak şekilde randevularını görüntüleyebilmektedir
doktor muayne notu veya kısa açıklama reçete giriceği alan olmalıdır
doktor ve hasta sistem üzer tc , adı ve soyadı ile aramalıdır
doktor ve hasta ikiside yapcak bunu doktor adı soy adı veya branşa göre doktor arayabilir
belirli bir tarih arasında oluşturulan randevuları görüntüleme
STABEL — 19:58
sistem tarihe göre randevu sorgulama imkanı sunmalıdır , girilen tarighler arasında belşrli bir doktora ait randevular görünmelidir
girilen tarihler arasında belirli bir hastaya ait randevular listelenebilmektedir
randevu oluşturma ekranında doktora ait müsait saatler gösterilmelidir,
dolmuş satlerde randevu verilmemelidir
kullanıcılar kendi ekranlarında iletişimö bilgilerini görüntülüyebilmeli ve güncelleye bilmelidir
gerekirse kendi ekranlarında şifre değiştirmelidir
doktor kendi ekranında kendi ekranında çalıştığı branş çalıştığı poliklinik ve çalışma saatlerini görüntülemelidir
uygunluk saatlerini belirleyebilmektedir
----------------------
kullanıcılar yanlızca kendi rolüne uygun verilere erişebilmelidir örneğin bir hasta başka hastanın verilerini görememelidir
houdini — 21:38
clamfdes
houdini — 22:53
crud işlevselliği yapmak zorunlu kontrol eder misin
singleton tasarım deseni kullanılmak zorundadır
factory veya abstract factory tasarım desenini kullanmak zorunludur
observer tasarım desenini kullanmak zorunludur
state tasarım desenini kullanmak zorunludur
iki farklı tasarım desenini de kendin belirleyip kullanmalısın
projede en az iki abstract class kullanılması zorunludur
STABEL — 23:08
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;... (8 KB kaldı)
Genişlet
message.txt
58 KB
houdini — 23:09
factory veya absctract factor yok, observer yok, state yok 2 abstractımız yok
houdini — 23:18
kullanıcı rolleri için use-case diyagramı çizilicek
veri tabanı şeması için er diyagramı çizilicek
abstract class ların class diyagları çizlimeli
kullanıcı rollerinden herhangi biri için sequence diyagramı mutlaka çizlilmelidir
STABEL — 23:21
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;... (11 KB kaldı)
Genişlet
message.txt
61 KB
houdini — 23:34
Görsel
Görsel
Görsel
Görsel
Görsel
STABEL — 23:36
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;... (11 KB kaldı)
Genişlet
Yeni Metin Belgesi.txt
61 KB
memoss7517 — 23:44
ç
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

Bu proje eğitim amaçlıdır ve serbestçe geliştirilebilir.
message.txt
4 KB
