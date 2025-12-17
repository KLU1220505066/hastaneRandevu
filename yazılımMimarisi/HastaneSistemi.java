import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

// ==========================================
// 1. ABSTRACT CLASSES & MODELS
// ==========================================

// Abstract Class 1: Temel Kullanıcı Sınıfı
abstract class User {
    protected int id;
    protected String name;
    protected String username;
    protected String password;
    protected String contactInfo;

    public User(int id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.contactInfo = "Telefon Girilmedi";
    }

    public abstract String getRole(); // Polymorphism

    // Getter & Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
}

class Patient extends User implements IObserver {
    public Patient(int id, String name, String username, String password) {
        super(id, name, username, password);
    }

    @Override
    public String getRole() { return "Hasta"; }

    @Override
    public void update(String message) {
        JOptionPane.showMessageDialog(null, "Sayın " + name + ", Bildirim: " + message);
    }
}

class Doctor extends User {
    private String branch;
    private List<String> availableHours;

    public Doctor(int id, String name, String username, String password, String branch) {
        super(id, name, username, password);
        this.branch = branch;
        this.availableHours = new ArrayList<>(Arrays.asList("09:00", "10:00", "11:00", "13:00", "14:00", "15:00"));
    }

    @Override
    public String getRole() { return "Doktor"; }
    public String getBranch() { return branch; }
    public List<String> getAvailableHours() { return availableHours; }
    public void setAvailableHours(List<String> hours) { this.availableHours = hours; }
}

// ==========================================
// 2. DESIGN PATTERN: STATE (Randevu Durumu)
// ==========================================
interface AppointmentState {
    void handleCancel(Appointment context);
    void handleComplete(Appointment context);
    String getStateName();
}

class ScheduledState implements AppointmentState {
    @Override
    public void handleCancel(Appointment context) {
        context.setState(new CancelledState());
        context.notifyObservers("Randevunuz iptal edildi.");
    }
    @Override
    public void handleComplete(Appointment context) {
        context.setState(new CompletedState());
        context.notifyObservers("Randevu tamamlandı olarak işaretlendi.");
    }
    @Override
    public String getStateName() { return "Aktif"; }
}

class CancelledState implements AppointmentState {
    @Override
    public void handleCancel(Appointment context) {
        // Zaten iptal
    }
    @Override
    public void handleComplete(Appointment context) {
        // İptal edilen tamamlanamaz
    }
    @Override
    public String getStateName() { return "İptal Edildi"; }
}

class CompletedState implements AppointmentState {
    @Override
    public void handleCancel(Appointment context) {
        // Tamamlanan iptal edilemez
    }
    @Override
    public void handleComplete(Appointment context) {
        // Zaten tamamlandı
    }
    @Override
    public String getStateName() { return "Tamamlandı"; }
}

// ==========================================
// 3. DESIGN PATTERN: OBSERVER & BUILDER
// ==========================================

interface IObserver {
    void update(String message);
}

class Appointment {
    private int id;
    private Patient patient;
    private Doctor doctor;
    private LocalDate date;
    private String time;
    private AppointmentState state; // STATE Pattern
    private List<IObserver> observers = new ArrayList<>();

    // Private constructor for BUILDER
    private Appointment(Builder builder) {
        this.id = builder.id;
        this.patient = builder.patient;
        this.doctor = builder.doctor;
        this.date = builder.date;
        this.time = builder.time;
        this.state = new ScheduledState(); // Initial State
        addObserver(patient); // Hasta otomatik observer olur
    }

    // OBSERVER Pattern Methods
    public void addObserver(IObserver o) { observers.add(o); }
    public void notifyObservers(String msg) {
        for(IObserver o : observers) o.update(msg);
    }

    public void setState(AppointmentState state) { this.state = state; }
    public void cancel() { state.handleCancel(this); }
    public void complete() { state.handleComplete(this); }
    
    // Getters
    public int getId() { return id; }
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public LocalDate getDate() { return date; }
    public String getTime() { return time; }
    public String getStatus() { return state.getStateName(); }

    // BUILDER Pattern (Ekstra Desen 1)
    public static class Builder {
        private int id;
        private Patient patient;
        private Doctor doctor;
        private LocalDate date;
        private String time;

        public Builder(int id) { this.id = id; }
        public Builder patient(Patient p) { this.patient = p; return this; }
        public Builder doctor(Doctor d) { this.doctor = d; return this; }
        public Builder date(LocalDate d) { this.date = d; return this; }
        public Builder time(String t) { this.time = t; return this; }
        public Appointment build() { return new Appointment(this); }
    }
}

// ==========================================
// 4. DESIGN PATTERN: STRATEGY (Arama) - Ekstra Desen 2
// ==========================================
interface SearchStrategy<T> {
    List<T> search(List<T> source, String query);
}

class SearchDoctorByName implements SearchStrategy<Doctor> {
    @Override
    public List<Doctor> search(List<Doctor> source, String query) {
        return source.stream()
                .filter(d -> d.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}

class SearchDoctorByBranch implements SearchStrategy<Doctor> {
    @Override
    public List<Doctor> search(List<Doctor> source, String query) {
        return source.stream()
                .filter(d -> d.getBranch().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}

// ==========================================
// 5. DESIGN PATTERN: FACTORY
// ==========================================
class UserFactory {
    public static User createUser(String type, int id, String name, String user, String pass, String extra) {
        if (type.equalsIgnoreCase("HASTA")) {
            return new Patient(id, name, user, pass);
        } else if (type.equalsIgnoreCase("DOKTOR")) {
            return new Doctor(id, name, user, pass, extra);
        }
        return null;
    }
}

// ==========================================
// 6. DESIGN PATTERN: SINGLETON (Veritabanı)
// ==========================================
class DatabaseManager {
    private static DatabaseManager instance;
    private List<User> users;
    private List<Appointment> appointments;
    private int appointmentIdCounter = 1;

    private DatabaseManager() {
        users = new ArrayList<>();
        appointments = new ArrayList<>();
        // Seed Data
        users.add(UserFactory.createUser("HASTA", 1, "Ahmet Yılmaz", "ahmet", "123", null));
        users.add(UserFactory.createUser("HASTA", 2, "Ayşe Demir", "ayse", "123", null));
        users.add(UserFactory.createUser("DOKTOR", 3, "Dr. Mehmet Öz", "mehmet", "123", "Kardiyoloji"));
        users.add(UserFactory.createUser("DOKTOR", 4, "Dr. Canan Karatay", "canan", "123", "Dahiliye"));
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) instance = new DatabaseManager();
        return instance;
    }

    public User login(String username, String password) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst().orElse(null);
    }

    public void addAppointment(Appointment apt) { appointments.add(apt); }
    public List<Appointment> getAppointments() { return appointments; }
    public List<User> getUsers() { return users; }
    public int getNextAppointmentId() { return appointmentIdCounter++; }
    
    // Basit filtrelemeler
    public List<Doctor> getAllDoctors() {
        List<Doctor> docs = new ArrayList<>();
        for(User u : users) if(u instanceof Doctor) docs.add((Doctor)u);
        return docs;
    }
    
    public boolean isSlotTaken(Doctor d, LocalDate date, String time) {
        return appointments.stream()
                .anyMatch(a -> a.getDoctor().equals(d) && a.getDate().equals(date) && a.getTime().equals(time) 
                        && !a.getStatus().equals("İptal Edildi"));
    }
}

// ==========================================
// 7. GUI CLASSES & ABSTRACT SCREEN
// ==========================================

// Abstract Class 2: Temel Ekran Yapısı
abstract class BaseScreen extends JFrame {
    protected User currentUser;
    protected DatabaseManager db;

    public BaseScreen(String title, User user) {
        this.currentUser = user;
        this.db = DatabaseManager.getInstance();
        setTitle(title);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    protected abstract void initUI();

    protected void logout() {
        new LoginScreen().setVisible(true);
        this.dispose();
    }
}

class LoginScreen extends JFrame {
    public LoginScreen() {
        setTitle("Hastane Randevu Sistemi - Giriş");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2));

        JTextField txtUser = new JTextField();
        JPasswordField txtPass = new JPasswordField();
        JButton btnLogin = new JButton("Giriş Yap");

        add(new JLabel("Kullanıcı Adı:")); add(txtUser);
        add(new JLabel("Şifre:")); add(txtPass);
        add(new JLabel("")); add(btnLogin);

        btnLogin.addActionListener(e -> {
            User user = DatabaseManager.getInstance().login(txtUser.getText(), new String(txtPass.getPassword()));
            if (user != null) {
                if (user instanceof Patient) new PatientDashboard((Patient) user).setVisible(true);
                else new DoctorDashboard((Doctor) user).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Hatalı Giriş!");
            }
        });
    }
}

class PatientDashboard extends BaseScreen {
    private JTable appointmentTable;
    private DefaultTableModel tableModel;

    public PatientDashboard(Patient patient) {
        super("Hasta Paneli - " + patient.getName(), patient);
    }

    @Override
    protected void initUI() {
        JTabbedPane tabs = new JTabbedPane();

        // TAB 1: Randevu Al
        JPanel pnlBook = new JPanel(new GridLayout(5, 2));
        JComboBox<Doctor> cmbDoctors = new JComboBox<>();
        db.getAllDoctors().forEach(cmbDoctors::addItem);
        
        JTextField txtDate = new JTextField("2025-10-25"); // Basitlik için String format
        JComboBox<String> cmbTime = new JComboBox<>(new String[]{"09:00", "10:00", "11:00", "13:00", "14:00"});
        JButton btnBook = new JButton("Randevu Oluştur");

        pnlBook.add(new JLabel("Doktor Seç:")); pnlBook.add(cmbDoctors);
        pnlBook.add(new JLabel("Tarih (YYYY-AA-GG):")); pnlBook.add(txtDate);
        pnlBook.add(new JLabel("Saat:")); pnlBook.add(cmbTime);
        pnlBook.add(new JLabel("")); pnlBook.add(btnBook);

        btnBook.addActionListener(e -> {
            try {
                Doctor selDoc = (Doctor) cmbDoctors.getSelectedItem();
                LocalDate date = LocalDate.parse(txtDate.getText());
                String time = (String) cmbTime.getSelectedItem();

                if (db.isSlotTaken(selDoc, date, time)) {
                    JOptionPane.showMessageDialog(this, "Bu saat dolu!");
                    return;
                }

                // BUILDER KULLANIMI
                Appointment apt = new Appointment.Builder(db.getNextAppointmentId())
                        .patient((Patient) currentUser)
                        .doctor(selDoc)
                        .date(date)
                        .time(time)
                        .build();

                db.addAppointment(apt);
                refreshTable();
                JOptionPane.showMessageDialog(this, "Randevu oluşturuldu!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Tarih formatı hatalı: YYYY-AA-GG");
            }
        });

        // TAB 2: Randevularım
        String[] cols = {"ID", "Doktor", "Branş", "Tarih", "Saat", "Durum"};
        tableModel = new DefaultTableModel(cols, 0);
        appointmentTable = new JTable(tableModel);
        JButton btnCancel = new JButton("Seçili Randevuyu İptal Et");

        btnCancel.addActionListener(e -> {
            int row = appointmentTable.getSelectedRow();
            if (row >= 0) {
                int id = (int) tableModel.getValueAt(row, 0);
                Appointment apt = db.getAppointments().stream().filter(a -> a.getId() == id).findFirst().orElse(null);
                if (apt != null) {
                    apt.cancel(); // STATE Pattern
                    refreshTable();
                }
            }
        });

        JPanel pnlList = new JPanel(new BorderLayout());
        pnlList.add(new JScrollPane(appointmentTable), BorderLayout.CENTER);
        pnlList.add(btnCancel, BorderLayout.SOUTH);

        // TAB 3: Profil
        JPanel pnlProfile = new JPanel(new GridLayout(4, 2));
        JTextField txtPhone = new JTextField(currentUser.getContactInfo());
        JPasswordField txtPass = new JPasswordField(currentUser.getPassword());
        JButton btnUpdate = new JButton("Güncelle");

        pnlProfile.add(new JLabel("Telefon:")); pnlProfile.add(txtPhone);
        pnlProfile.add(new JLabel("Yeni Şifre:")); pnlProfile.add(txtPass);
        pnlProfile.add(new JLabel("")); pnlProfile.add(btnUpdate);

        btnUpdate.addActionListener(e -> {
            currentUser.setContactInfo(txtPhone.getText());
            currentUser.setPassword(new String(txtPass.getPassword()));
            JOptionPane.showMessageDialog(this, "Bilgiler Güncellendi");
        });

        tabs.addTab("Randevu Al", pnlBook);
        tabs.addTab("Randevularım", pnlList);
        tabs.addTab("Profilim", pnlProfile);
        
        JButton btnLogout = new JButton("Çıkış Yap");
        btnLogout.addActionListener(e -> logout());

        add(tabs, BorderLayout.CENTER);
        add(btnLogout, BorderLayout.SOUTH);
        
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Appointment a : db.getAppointments()) {
            if (a.getPatient().getId() == currentUser.getId()) {
                tableModel.addRow(new Object[]{
                        a.getId(), a.getDoctor().getName(), a.getDoctor().getBranch(), a.getDate(), a.getTime(), a.getStatus()
                });
            }
        }
    }
}

class DoctorDashboard extends BaseScreen {
    private JTable table;
    private DefaultTableModel model;
    private SearchStrategy<Doctor> searchStrategy; // STRATEGY pattern referansı (Burada mantıken hasta aramalı ama örnek için)

    public DoctorDashboard(Doctor doctor) {
        super("Doktor Paneli - " + doctor.getName(), doctor);
    }

    @Override
    protected void initUI() {
        JTabbedPane tabs = new JTabbedPane();

        // TAB 1: Randevu Listesi
        String[] cols = {"ID", "Hasta", "Tarih", "Saat", "Durum"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        
        JPanel pnlActions = new JPanel();
        JButton btnComplete = new JButton("Randevu Tamamlandı");
        JButton btnNoshow = new JButton("Hasta Gelmedi (İptal)");
        
        pnlActions.add(btnComplete);
        pnlActions.add(btnNoshow);

        btnComplete.addActionListener(e -> updateStatus(true));
        btnNoshow.addActionListener(e -> updateStatus(false));

        JPanel pnlList = new JPanel(new BorderLayout());
        pnlList.add(new JScrollPane(table), BorderLayout.CENTER);
        pnlList.add(pnlActions, BorderLayout.SOUTH);

        // TAB 2: Profil ve Ayarlar
        JPanel pnlSettings = new JPanel(new GridLayout(3, 2));
        JTextField txtBranch = new JTextField(((Doctor)currentUser).getBranch());
        txtBranch.setEditable(false);
        pnlSettings.add(new JLabel("Branşım:")); pnlSettings.add(txtBranch);

        tabs.addTab("Randevu Yönetimi", pnlList);
        tabs.addTab("Ayarlar", pnlSettings);

        add(tabs, BorderLayout.CENTER);
        
        JButton btnLogout = new JButton("Çıkış Yap");
        btnLogout.addActionListener(e -> logout());
        add(btnLogout, BorderLayout.SOUTH);

        refreshTable();
    }

    private void updateStatus(boolean isComplete) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int id = (int) model.getValueAt(row, 0);
            Appointment apt = db.getAppointments().stream().filter(a -> a.getId() == id).findFirst().orElse(null);
            if (apt != null) {
                if(isComplete) apt.complete();
                else apt.cancel();
                refreshTable();
            }
        }
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (Appointment a : db.getAppointments()) {
            if (a.getDoctor().getId() == currentUser.getId()) {
                model.addRow(new Object[]{
                        a.getId(), a.getPatient().getName(), a.getDate(), a.getTime(), a.getStatus()
                });
            }
        }
    }
}

// ==========================================
// MAIN CLASS
// ==========================================
public class HastaneSistemi {
    public static void main(String[] args) {
        // Swing arayüzünü güvenli thread'de başlat
        SwingUtilities.invokeLater(() -> {
            new LoginScreen().setVisible(true);
        });
    }
}