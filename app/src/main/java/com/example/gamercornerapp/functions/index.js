
const { onDocumentCreated } = require("firebase-functions/v2/firestore");
const admin = require("firebase-admin");

admin.initializeApp();

const db = admin.firestore();

// Notificar cuando alguien recibe un like en una reseña
exports.sendReviewLikeNotification = onDocumentCreated(
    "reviews/{reviewId}/likes/{userId}",
    async (event) => {

        const reviewId = event.params.reviewId;
        const userId = event.params.userId;

        const reviewDoc = await db
            .collection("reviews")
            .doc(reviewId)
            .get();

        if (!reviewDoc.exists) {
            console.log("La reseña no existe");
            return;
        }

        const reviewData = reviewDoc.data();
        const authorId = reviewData.userId;

        // No notificar likes propios
        if (!authorId || authorId === userId) {
            return;
        }

        const authorDoc = await db
            .collection("users")
            .doc(authorId)
            .get();

        if (!authorDoc.exists) {
            console.log("El autor de la reseña no existe");
            return;
        }

        const authorData = authorDoc.data();

        // Campo utilizado por UserProfileDTO en Firestore
        const userToken = authorData.fcm_token;

        const userDoc = await db
            .collection("users")
            .doc(userId)
            .get();

        const username = userDoc.exists
            ? userDoc.data().username || "Un usuario"
            : "Un usuario";

        const gameTitle =
            reviewData.game?.title || "un videojuego";

        // Guardar notificación en Firestore
        // Usar un ID fijo evita duplicados si la función se reintenta
        await db
            .collection("users")
            .doc(authorId)
            .collection("notifications")
            .doc(`like_${reviewId}_${userId}`)
            .set({
                type: "LIKE",
                username: username,
                actionText: "le dio like a tu reseña de",
                targetTitle: gameTitle,
                reviewId: reviewId,
                fromUserId: userId,
                createdAt: admin.firestore.FieldValue.serverTimestamp()
            });

        console.log(
            `Notificación guardada para ${authorId}`
        );

        // Enviar notificación push si existe un token
        if (userToken) {

            const message = {
                token: userToken,

                notification: {
                    title: "¡Nuevo like en GamerCorner!",
                    body: `${username} le dio like a tu reseña de ${gameTitle}`
                }
            };

            try {
                await admin.messaging().send(message);

                console.log(
                    `Notificación push enviada a ${authorId}`
                );

            } catch (error) {
                console.error(
                    "Error al enviar la notificación push:",
                    error
                );
            }

        } else {
            console.log(
                `El usuario ${authorId} no tiene token FCM`
            );
        }
    }
);
