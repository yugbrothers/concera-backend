package com.uitmerch.backend.common.config;

import com.uitmerch.backend.auth.entity.User;
import com.uitmerch.backend.auth.repository.UserRepository;
import com.uitmerch.backend.cart.entity.Cart;
import com.uitmerch.backend.cart.entity.CartItem;
import com.uitmerch.backend.cart.repository.CartItemRepository;
import com.uitmerch.backend.cart.repository.CartRepository;
import com.uitmerch.backend.common.model.*;
import com.uitmerch.backend.event.entity.Event;
import com.uitmerch.backend.event.entity.EventMerch;
import com.uitmerch.backend.event.repository.EventMerchRepository;
import com.uitmerch.backend.event.repository.EventRepository;
import com.uitmerch.backend.merch.entity.Category;
import com.uitmerch.backend.merch.entity.MerchImage;
import com.uitmerch.backend.merch.entity.MerchItem;
import com.uitmerch.backend.merch.repository.CategoryRepository;
import com.uitmerch.backend.merch.repository.MerchImageRepository;
import com.uitmerch.backend.merch.repository.MerchItemRepository;
import com.uitmerch.backend.order.entity.Order;
import com.uitmerch.backend.order.entity.OrderItem;
import com.uitmerch.backend.order.repository.OrderItemRepository;
import com.uitmerch.backend.order.repository.OrderRepository;
import com.uitmerch.backend.organization.entity.Organization;
import com.uitmerch.backend.organization.repository.OrganizationRepository;
import com.uitmerch.backend.wishlist.entity.Wishlist;
import com.uitmerch.backend.wishlist.entity.WishlistItem;
import com.uitmerch.backend.wishlist.repository.WishlistItemRepository;
import com.uitmerch.backend.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@Profile("dev | docker")
@RequiredArgsConstructor
@Slf4j
public class DevDataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final CategoryRepository categoryRepository;
    private final MerchItemRepository merchItemRepository;
    private final MerchImageRepository merchImageRepository;
    private final EventRepository eventRepository;
    private final EventMerchRepository eventMerchRepository;
    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() > 0) {
            log.info("Sample data already present, skipping seed.");
            return;
        }
        log.info("Seeding Concera sample data...");

        // ── Users ─────────────────────────────────────────────────────────────
        User admin = saveUser("admin@uit.edu.vn",  "Admin123",  "Admin User",       null,          null,                              UserRole.ADMIN);
        User org1  = saveUser("org1@uit.edu.vn",   "Org12345",  "Le Van Cuong",     "0901111111", "Concera Campus, Thu Duc, TP.HCM", UserRole.ORGANIZER);
        User org2  = saveUser("org2@uit.edu.vn",   "Org12345",  "Pham Thi Dung",    "0902222222", "Concera Campus, Thu Duc, TP.HCM", UserRole.ORGANIZER);
        User cust1 = saveUser("cust1@uit.edu.vn",  "Cust1234",  "Nguyen Van A",     "0903333333", "123 Nguyen Van Cu, Q5, TP.HCM",   UserRole.CUSTOMER);
        User cust2 = saveUser("cust2@uit.edu.vn",  "Cust1234",  "Tran Thi B",       "0904444444", "456 Le Van Viet, Q9, TP.HCM",     UserRole.CUSTOMER);

        // ── Categories ────────────────────────────────────────────────────────
        Category catApparel     = saveCategory("apparel",        "Apparel",          "Hoodies, t-shirts, jackets, and caps",    1);
        Category catAccessories = saveCategory("accessories",    "Accessories",      "Stickers, keychains, badges, and lanyards", 2);
        Category catDrinkware   = saveCategory("drinkware-gear", "Drinkware & Gear", "Water bottles, mugs, and notebooks",      3);
        Category catBags        = saveCategory("bags",           "Bags",             "Canvas tote bags and backpacks",          4);

        // ── Organizations ─────────────────────────────────────────────────────
        Organization gdgOrg = saveOrg(org1.getId(), "Google Developer Groups Concera",
                "Google Developer Student Club community at Concera.",
                "https://placehold.co/400x400/92fbff/1a3a4a?font=montserrat&text=GDG",
                "https://placehold.co/1200x400/92fbff/1a3a4a?font=montserrat&text=GDG+Concera",
                OrganizationStatus.ACTIVE);

        Organization csOrg = saveOrg(org1.getId(), "Computer Science Club",
                "Computer science research and practical applications.",
                "https://placehold.co/400x400/f8e697/1a3a4a?font=montserrat&text=CS",
                "https://placehold.co/1200x400/f8e697/1a3a4a?font=montserrat&text=CS+Club",
                OrganizationStatus.ACTIVE);

        Organization secOrg = saveOrg(org1.getId(), "Concera Security Research",
                "Cybersecurity and system defense research.",
                "https://placehold.co/400x400/d4f0ff/1a3a4a?font=montserrat&text=SEC",
                "https://placehold.co/1200x400/d4f0ff/1a3a4a?font=montserrat&text=Concera+Sec",
                OrganizationStatus.ACTIVE);

        Organization gameOrg = saveOrg(org1.getId(), "Concera Game Developers",
                "Indie game design and interactive development.",
                "https://placehold.co/400x400/ffe4b5/1a3a4a?font=montserrat&text=GAME",
                "https://placehold.co/1200x400/ffe4b5/1a3a4a?font=montserrat&text=Concera+GameDev",
                OrganizationStatus.ACTIVE);

        Organization csaOrg = saveOrg(org1.getId(), "Concera Student Association",
                "Official representative organization of all Concera students.",
                "https://placehold.co/400x400/e0ffe4/1a3a4a?font=montserrat&text=CSA",
                "https://placehold.co/1200x400/e0ffe4/1a3a4a?font=montserrat&text=Concera+CSA",
                OrganizationStatus.ACTIVE);

        Organization aiOrg = saveOrg(org1.getId(), "Artificial Intelligence Club",
                "Machine Learning and applied AI research community.",
                "https://placehold.co/400x400/ffe0f0/1a3a4a?font=montserrat&text=AI",
                "https://placehold.co/1200x400/ffe0f0/1a3a4a?font=montserrat&text=AI+Club",
                OrganizationStatus.ACTIVE);

        Organization umdOrg = saveOrg(org2.getId(), "Concera Media & Design",
                "Visual design, media production, and campus communications.",
                "https://placehold.co/400x400/fff7cc/1a3a4a?font=montserrat&text=UMD",
                "https://placehold.co/1200x400/fff7cc/1a3a4a?font=montserrat&text=Concera+Media",
                OrganizationStatus.ACTIVE);

        // ── Merch (12 Concera Items with Images & Categories) ─────────────────
        MerchItem stickerPack = saveMerch(gdgOrg.getId(), catAccessories.getId(), "GDG Sticker Pack",
                "Unique water-resistant GDG logo sticker pack, perfect for laptops or water bottles.",
                new BigDecimal("15000"), 48, MerchItemStatus.PUBLISHED,
                "https://placehold.co/900x900/92fbff/1a3a4a?font=montserrat&text=GDG");

        MerchItem hoodie = saveMerch(csaOrg.getId(), catApparel.getId(), "Concera Hoodie",
                "Premium unisex cotton hoodie featuring the prominent Concera logo on the chest.",
                new BigDecimal("380000"), 18, MerchItemStatus.PUBLISHED,
                "https://placehold.co/900x900/f8e697/1a3a4a?font=montserrat&text=HOODIE");

        MerchItem bottle = saveMerch(csaOrg.getId(), catDrinkware.getId(), "Concera Water Bottle",
                "500ml insulated stainless steel water bottle with Concera logo, convenient on the go.",
                new BigDecimal("120000"), 24, MerchItemStatus.PUBLISHED,
                "https://placehold.co/900x900/d4f0ff/1a3a4a?font=montserrat&text=BOTTLE");

        MerchItem cap = saveMerch(csaOrg.getId(), catApparel.getId(), "Concera Baseball Cap",
                "Blue and white baseball cap showcasing the proud Concera spirit.",
                new BigDecimal("95000"), 31, MerchItemStatus.PUBLISHED,
                "https://placehold.co/900x900/ffe4b5/1a3a4a?font=montserrat&text=CAP");

        MerchItem tote = saveMerch(umdOrg.getId(), catBags.getId(), "Canvas Tote Bag",
                "Eco-friendly canvas tote bag with minimalist graphics designed by the UMD team.",
                new BigDecimal("75000"), 27, MerchItemStatus.PUBLISHED,
                "https://placehold.co/900x900/e0ffe4/1a3a4a?font=montserrat&text=TOTE");

        MerchItem tshirt = saveMerch(csOrg.getId(), catApparel.getId(), "CS2024 Commemorative T-Shirt",
                "CS2024 commemorative tee featuring artistic circuit board graphics on the back.",
                new BigDecimal("220000"), 14, MerchItemStatus.PUBLISHED,
                "https://placehold.co/900x900/ffe0f0/1a3a4a?font=montserrat&text=TSHIRT");

        MerchItem keychain = saveMerch(gdgOrg.getId(), catAccessories.getId(), "Golden Dragon Keychain",
                "Limited edition golden dragon enamel pin keychain distributed at GDG DevFest.",
                new BigDecimal("35000"), 36, MerchItemStatus.PUBLISHED,
                "https://placehold.co/900x900/fff7cc/1a3a4a?font=montserrat&text=KEY");

        MerchItem notebook = saveMerch(csaOrg.getId(), catDrinkware.getId(), "Concera Notebook 2025",
                "200-page hardcover notebook with Concera logo and premium bleed-resistant 100gsm paper.",
                new BigDecimal("55000"), 62, MerchItemStatus.PUBLISHED,
                "https://placehold.co/900x900/e8e8ff/1a3a4a?font=montserrat&text=NOTE");

        MerchItem badge = saveMerch(csaOrg.getId(), catAccessories.getId(), "Pin Badge Logo Concera",
                "Official Concera pin badge gifted to new students during Freshman Welcome 2025.",
                new BigDecimal("25000"), 80, MerchItemStatus.PUBLISHED,
                "https://placehold.co/900x900/ffd6cc/1a3a4a?font=montserrat&text=BADGE");

        MerchItem mug = saveMerch(gdgOrg.getId(), catDrinkware.getId(), "GDG Ceramic Mug",
                "Premium 350ml ceramic mug with GDG's signature blue gradient design.",
                new BigDecimal("85000"), 22, MerchItemStatus.PUBLISHED,
                "https://placehold.co/900x900/ccf5ff/1a3a4a?font=montserrat&text=MUG");

        MerchItem lanyard = saveMerch(csaOrg.getId(), catAccessories.getId(), "Concera Lanyard Strap",
                "Official student ID lanyard distributed during orientation week.",
                new BigDecimal("30000"), 100, MerchItemStatus.PUBLISHED,
                "https://placehold.co/900x900/f0e4ff/1a3a4a?font=montserrat&text=STRAP");

        MerchItem jacket = saveMerch(csaOrg.getId(), catApparel.getId(), "Concera Windbreaker Jacket",
                "2-layer windbreaker jacket with embroidered Concera logo on the left chest and zip pockets.",
                new BigDecimal("450000"), 12, MerchItemStatus.PUBLISHED,
                "https://placehold.co/900x900/dcffe8/1a3a4a?font=montserrat&text=JACKET");

        // ── Events ────────────────────────────────────────────────────────────
        Event techFair = saveEvent(gdgOrg.getId(), "Concera Tech Fair 2026",
                "Annual technology showcase with student projects, industry speakers, and exclusive Concera merchandise.",
                "https://placehold.co/1200x600/92fbff/1a3a4a?font=montserrat&text=Concera+Tech+Fair+2026",
                EventStatus.PUBLISHED,
                LocalDateTime.of(2026, 11, 1, 8, 0), LocalDateTime.of(2026, 11, 1, 17, 0));

        Event campusFest = saveEvent(csaOrg.getId(), "Concera Campus Festival 2026",
                "Campus-wide student festival celebrating club culture, live music, and seasonal merchandise drops.",
                "https://placehold.co/1200x600/f8e697/1a3a4a?font=montserrat&text=Concera+Festival+2026",
                EventStatus.PUBLISHED,
                LocalDateTime.of(2026, 11, 15, 9, 0), LocalDateTime.of(2026, 11, 16, 21, 0));

        attachMerchToEvent(techFair.getId(), stickerPack.getId());
        attachMerchToEvent(techFair.getId(), hoodie.getId());
        attachMerchToEvent(techFair.getId(), keychain.getId());
        attachMerchToEvent(techFair.getId(), mug.getId());

        attachMerchToEvent(campusFest.getId(), hoodie.getId());
        attachMerchToEvent(campusFest.getId(), bottle.getId());
        attachMerchToEvent(campusFest.getId(), cap.getId());
        attachMerchToEvent(campusFest.getId(), jacket.getId());

        // ── Wishlist for cust1 ────────────────────────────────────────────────
        Wishlist wishlist = wishlistRepository.save(Wishlist.builder().userId(cust1.getId()).build());
        wishlistItemRepository.save(WishlistItem.builder().wishlistId(wishlist.getId()).merchId(hoodie.getId()).build());
        wishlistItemRepository.save(WishlistItem.builder().wishlistId(wishlist.getId()).merchId(cap.getId()).build());

        // ── Cart for cust1 (active, not checked out) ─────────────────────────
        Cart cart = cartRepository.save(Cart.builder().userId(cust1.getId()).build());
        cartItemRepository.save(CartItem.builder().cartId(cart.getId()).merchId(hoodie.getId()).quantity(1).build());
        cartItemRepository.save(CartItem.builder().cartId(cart.getId()).merchId(tshirt.getId()).quantity(2).build());

        // ── Orders ────────────────────────────────────────────────────────────
        saveOrder(cust1.getId(), csaOrg.getId(), OrderStatus.PENDING, null,
                List.of(new Line(hoodie, 2), new Line(bottle, 1)));

        saveOrder(cust1.getId(), gdgOrg.getId(), OrderStatus.CONFIRMED, "Please pack separately",
                List.of(new Line(stickerPack, 3), new Line(mug, 2)));

        saveOrder(cust2.getId(), csaOrg.getId(), OrderStatus.READY, null,
                List.of(new Line(cap, 2), new Line(jacket, 1)));

        saveGuestOrder(umdOrg.getId(),
                "Vo Thi C", "0905555555", "789 Vo Van Ngan, Thu Duc, TP.HCM", "guest@example.com",
                List.of(new Line(tote, 2), new Line(notebook, 1)));

        log.info("Concera sample data ready (7 organizations, 4 categories, 12 merch items with images, 2 events).");
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private User saveUser(String email, String rawPassword, String fullName,
                          String phone, String address, UserRole role) {
        return userRepository.save(User.builder()
                .email(email)
                .passwordHash(passwordEncoder.encode(rawPassword))
                .fullName(fullName)
                .phone(phone)
                .address(address)
                .role(role)
                .isVerified(true)
                .build());
    }

    private Category saveCategory(String slug, String name, String description, int displayOrder) {
        return categoryRepository.save(Category.builder()
                .slug(slug)
                .name(name)
                .description(description)
                .displayOrder(displayOrder)
                .build());
    }

    private Organization saveOrg(UUID ownerId, String name, String description,
                                  String logoUrl, String coverUrl, OrganizationStatus status) {
        return organizationRepository.save(Organization.builder()
                .ownerId(ownerId)
                .name(name)
                .description(description)
                .logoUrl(logoUrl)
                .coverUrl(coverUrl)
                .status(status)
                .build());
    }

    private MerchItem saveMerch(UUID orgId, UUID categoryId, String name, String description,
                                 BigDecimal price, int stock, MerchItemStatus status, String imageUrl) {
        MerchItem item = merchItemRepository.save(MerchItem.builder()
                .orgId(orgId)
                .categoryId(categoryId)
                .name(name)
                .description(description)
                .price(price)
                .stock(stock)
                .status(status)
                .build());

        if (imageUrl != null && !imageUrl.isBlank()) {
            merchImageRepository.save(MerchImage.builder()
                    .merchId(item.getId())
                    .url(imageUrl)
                    .position(0)
                    .build());
        }
        return item;
    }

    private Event saveEvent(UUID orgId, String title, String description, String coverUrl,
                             EventStatus status, LocalDateTime startsAt, LocalDateTime endsAt) {
        return eventRepository.save(Event.builder()
                .orgId(orgId)
                .title(title)
                .description(description)
                .coverUrl(coverUrl)
                .status(status)
                .startsAt(startsAt)
                .endsAt(endsAt)
                .build());
    }

    private void attachMerchToEvent(UUID eventId, UUID merchId) {
        eventMerchRepository.save(EventMerch.builder()
                .eventId(eventId)
                .merchId(merchId)
                .build());
    }

    private void saveOrder(UUID userId, UUID orgId, OrderStatus status, String note,
                            List<Line> lines) {
        BigDecimal total = lines.stream()
                .map(l -> l.merch().getPrice().multiply(BigDecimal.valueOf(l.qty())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = orderRepository.save(Order.builder()
                .userId(userId)
                .orgId(orgId)
                .totalAmount(total)
                .status(status)
                .paymentMethod(PaymentMethod.CASH_ON_DELIVERY)
                .paymentStatus(PaymentStatus.PENDING)
                .note(note)
                .build());

        lines.forEach(l -> saveOrderItem(order.getId(), l));
    }

    private void saveGuestOrder(UUID orgId, String guestName, String guestPhone,
                                 String guestAddress, String guestEmail, List<Line> lines) {
        BigDecimal total = lines.stream()
                .map(l -> l.merch().getPrice().multiply(BigDecimal.valueOf(l.qty())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = orderRepository.save(Order.builder()
                .orgId(orgId)
                .guestName(guestName)
                .guestPhone(guestPhone)
                .guestAddress(guestAddress)
                .guestEmail(guestEmail)
                .totalAmount(total)
                .status(OrderStatus.PENDING)
                .paymentMethod(PaymentMethod.CASH_ON_DELIVERY)
                .paymentStatus(PaymentStatus.PENDING)
                .build());

        lines.forEach(l -> saveOrderItem(order.getId(), l));
    }

    private void saveOrderItem(UUID orderId, Line l) {
        orderItemRepository.save(OrderItem.builder()
                .orderId(orderId)
                .merchId(l.merch().getId())
                .merchName(l.merch().getName())
                .unitPrice(l.merch().getPrice())
                .quantity(l.qty())
                .subtotal(l.merch().getPrice().multiply(BigDecimal.valueOf(l.qty())))
                .build());
    }

    private record Line(MerchItem merch, int qty) {}
}
