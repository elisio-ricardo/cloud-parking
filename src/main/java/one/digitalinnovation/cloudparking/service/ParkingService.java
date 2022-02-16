package one.digitalinnovation.cloudparking.service;


import one.digitalinnovation.cloudparking.exception.ParkingNotFoundExcption;
import one.digitalinnovation.cloudparking.model.Parking;
import one.digitalinnovation.cloudparking.repository.ParkingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ParkingService {

    ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    //
//    private static Map<String, Parking> parkingMap = new HashMap<>();
//
//    static {
//        var id = getUUID();
//        var id1 = getUUID();
//        Parking parking = new Parking(id, "DMS-1111", "SC", "Celta", "Preto");
//        Parking parking1 = new Parking(id1, "AWS-2222", "SP", "Punto", "Cinza");
//        parkingMap.put(id, parking);
//        parkingMap.put(id1, parking1);
//    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Parking> findAll() {
//        return parkingMap.values().stream().collect(Collectors.toList());
        return parkingRepository.findAll();
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Transactional(readOnly = true)
    public Parking findById(String id) {
        return parkingRepository.findById(id).orElseThrow(() ->
                new ParkingNotFoundExcption(id));

//        Parking parking = parkingMap.get(id);
//        if (parking == null) {
//            throw new ParkingNotFoundExcption(id);
//        }
//
//        return parking;

    }

    @Transactional
    public Parking create(Parking parkingCreate) {
        String uuid = getUUID();
        parkingCreate.setId(uuid);
        parkingCreate.setEntryDate(LocalDateTime.now());
//        parkingMap.put(uuid, parkingCreate);

        parkingRepository.save(parkingCreate);

        return parkingCreate;
    }


    @Transactional
    public void delete(String id) {
        Parking parking = findById(id);
//        parkingMap.remove(id);

        parkingRepository.delete(parking);
    }

    @Transactional
    public Parking update(String id, Parking parkingCreate) {
        Parking parking = findById(id);
        parking.setColor(parkingCreate.getColor());
//        parkingMap.replace(id, parking);

        parkingRepository.save(parking);
        return parking;
    }

    @Transactional
    public Parking checkOut(String id) {
        Parking parking = findById(id);
        parking.setExitDate(LocalDateTime.now());
        parking.setBill(ParkingCheckOut.getBill(parking));
        parkingRepository.save(parking);

        return parking;
    }
}

