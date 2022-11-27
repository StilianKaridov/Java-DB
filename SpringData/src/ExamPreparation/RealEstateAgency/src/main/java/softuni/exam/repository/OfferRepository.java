package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.ExportOfferDTO;
import softuni.exam.models.entity.Offer;
import softuni.exam.models.enums.ApartmentType;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query(value = "select new softuni.exam.models.dto.ExportOfferDTO" +
            "(o.agent.firstName, o.agent.lastName, o.id, o.apartment.area, o.apartment.town.townName, o.price) " +
            "from Offer o " +
            "where o.apartment.apartmentType = :apartmentType " +
            "order by o.apartment.area desc, o.price")
    List<ExportOfferDTO> getTheBestOffers(ApartmentType apartmentType);
}
