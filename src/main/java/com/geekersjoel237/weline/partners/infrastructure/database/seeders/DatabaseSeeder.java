package com.geekersjoel237.weline.partners.infrastructure.database.seeders;

import com.geekersjoel237.weline.partners.domain.entities.Partner;
import com.geekersjoel237.weline.partners.domain.repositories.PartnerRepository;
import com.geekersjoel237.weline.shared.domain.vo.Id;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final PartnerRepository partnerRepository;

    public DatabaseSeeder(
            PartnerRepository partnerRepository
    ) {
        this.partnerRepository = partnerRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        if (!partnerRepository.exists()) {
            System.out.println("Seeding initial data through domain...");

            Partner eneo = Partner.create(Id.generate(), "ENEO Cameroun");

            var agenceDeido = eneo.addServicePoint(
                    Id.generate(),
                    "Agence de Deido",
                    "Douala, Deido"
            );

            agenceDeido.addService(
                    Id.generate(),
                    Id.generate(),
                    "Paiement Facture",
                    "Payer vos factures d'électricité.",
                    "PAY-F"
            );
            agenceDeido.addService(
                    Id.generate(),
                    Id.generate(),
                    "Nouveau Compteur",
                    "Demander un nouveau compteur ou un abonnement.",
                    "CMP-N"
            );

            partnerRepository.addMany(List.of(eneo.snapshot()));

            System.out.println("Seeding complete.");
        }
    }
}
