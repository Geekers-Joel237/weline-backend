package com.geekersjoel237.weline.partners.infrastructure.database.seeders;

import com.geekersjoel237.weline.partners.domain.entities.Partner;
import com.geekersjoel237.weline.partners.domain.repositories.PartnerRepository;
import com.geekersjoel237.weline.queue.domain.entities.Queue;
import com.geekersjoel237.weline.queue.domain.repositories.QueueRepository;
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
    private final QueueRepository queueRepository;

    public DatabaseSeeder(
            PartnerRepository partnerRepository, QueueRepository queueRepository
    ) {
        this.partnerRepository = partnerRepository;
        this.queueRepository = queueRepository;
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

            var queueId1 = Id.generate();
            agenceDeido.addService(
                    Id.generate(),
                    queueId1,
                    "Paiement Facture",
                    "Payer vos factures d'électricité.",
                    "PAY-F"
            );

            var queueId2 = Id.generate();
            agenceDeido.addService(
                    Id.generate(),
                    queueId2,
                    "Nouveau Compteur",
                    "Demander un nouveau compteur ou un abonnement.",
                    "CMP-N"
            );

            var queue1 = Queue.create(
                    queueId1,
                    Id.of(agenceDeido.snapshot().services().getFirst().id())
            );
            var queue2 = Queue.create(
                    queueId2,
                    Id.of(agenceDeido.snapshot().services().get(1).id())
            );


            queueRepository.addMany(List.of(queue1.snapshot(), queue2.snapshot()));
            partnerRepository.addMany(List.of(eneo.snapshot()));

            System.out.println("Seeding complete.");
        }
    }
}
