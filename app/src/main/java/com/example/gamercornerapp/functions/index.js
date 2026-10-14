
const { onDocumentCreated } = require("firebase-functions/v2/firestore");
const admin = require("firebase-admin");

admin.initializeApp();

const db = admin.firestore();

// Funcion para notificar cuando alguien recibe un like
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
        const userToken = authorData.FCM_token;

        if (userToken) {

            const userDoc = await db
                .collection("users")
                .doc(userId)
                .get();

            const username = userDoc.exists
                ? userDoc.data().username || "Un usuario"
                : "Un usuario";

            const gameTitle =
                reviewData.game?.title || "un videojuego";

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
                    `Notificacion enviada a ${authorId}`
                );

            } catch (error) {
                console.error(
                    "Error al enviar la notificacion:",
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
