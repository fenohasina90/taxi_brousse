<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>

<body class="vertical-layout vertical-menu 2-columns fixed-navbar"
      data-open="click" data-menu="vertical-menu" data-col="2-columns">

<%@ include file="/WEB-INF/jsp/layout/sidebar.jsp"%>


<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="content-wrapper">

        <!-- Header -->
        <div class="content-header row">
            <div class="content-header-left col-md-6 col-12 mb-2">
                <h3 class="content-header-title">Formulaire - Creer un Reservation</h3>
            </div>
            <div class="content-header-right col-md-6 col-12 mb-2 text-right">
                <a href="/reservations" class="btn btn-secondary">
                    <i class="la la-list"></i> Liste des reservations
                </a>
            </div>
        </div>

        <div class="content-body">
            <section id="basic-form-layouts">
                <div class="row">
                    <div class="col-md-12">

                        <div class="card">
                            <div class="card-header">
                                <div class="heading-elements">
                                    <ul class="list-inline mb-0">
                                        <li><a data-action="collapse"><i class="ft-minus"></i></a></li>
                                        <li><a data-action="reload"><i class="ft-rotate-cw"></i></a></li>
                                        <li><a data-action="expand"><i class="ft-maximize"></i></a></li>
                                        <li><a data-action="close"><i class="ft-x"></i></a></li>
                                    </ul>
                                </div>
                            </div>

                            <div class="card-content collpase show">
                                <div class="card-body">

                                    <!-- MESSAGE D'ERREUR -->
                                    <c:if test="${not empty errorMessage}">
                                        <div class="alert alert-danger">
                                            ${errorMessage}
                                        </div>
                                    </c:if>

                                    <!-- FORMULAIRE -->
                                    <form class="form form-horizontal form-bordered"
                                          method="post"
                                          action="${pageContext.request.contextPath}/reservations/ajouter">

                                        <div class="form-body">

                                            <input type="hidden" name="idVoyageDetails" value="${idVoyageDetails}">

                                            <!-- Clients -->
                                            <div class="form-group row">
                                                <label class="col-md-3 label-control">
                                                    Nom du client
                                                </label>
                                                <div class="col-md-9">
                                                    <input type="text"
                                                           name="nom_client"
                                                           class="form-control"
                                                           placeholder="Nom du client ..."
                                                           required>
                                                </div>
                                            </div>

                                            
                                            <div class="form-group row">
                                                <label class="col-md-3 label-control">
                                                    Contact du client
                                                </label>
                                                <div class="col-md-9">
                                                    <input type="text"
                                                           name="contactClient"
                                                           class="form-control"
                                                           placeholder="Ex: 03X XX XXX XX ..."
                                                           required>
                                                </div>
                                            </div>

                                            <!-- Paiement -->
                                            <div class="form-group row">
                                                <label class="col-md-3 label-control">Type de paiement</label>
                                                <div class="col-md-9">
                                                    <select name="typePaiement" class="form-control">
                                                        <option value="">-- Choisir un type de paiement --</option>
                                                        <c:forEach var="t" items="${modePaiement}">
                                                            <option value="${t.id}">
                                                                ${t.mode}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            
                                            <input type="hidden" id="montantUnitaire" value="${montantUnitaire}">

                                            <div class="form-group row">
                                                <label class="col-md-3 label-control">
                                                    Montant total a payer (Ar)
                                                </label>
                                                <div class="col-md-9">
                                                    <input type="text"
                                                        id="montantAffiche"
                                                        class="form-control mt-1"
                                                        readonly
                                                        value="0 Ar">
                                                </div>
                                                
                                            </div>

                                            <div class="form-group row">
                                                <label class="col-md-3 label-control">
                                                    Montant partiel ou total
                                                </label>
                                                <div class="col-md-9">
                                                    <input type="number"
                                                            name="montant"
                                                            class="form-control"
                                                            placeholder="Montant en Ariary ...">
                                                </div>
                                            </div>

                                            
                                            


                                            

                                            <!-- Place -->


                                            <!-- Sélection des places -->
                                            <div class="form-group row">
                                                <label class="col-md-3 label-control">
                                                    Choix des places
                                                </label>

                                                <div class="col-md-9">

                                                    <div class="seat-container">

                                                        <!-- Première ligne : case chauffeur désactivée + places 1 et 2 -->
                                                        <div class="seat-row">
                                                            <!-- Case chauffeur (toujours désactivée) -->
                                                            <label class="seat-disabled chauffeur">
                                                                <input type="checkbox" disabled>
                                                                <span>chauffeur</span>
                                                            </label>
                                                        

                                                            <!-- Places 1 et 2 -->
                                                            <c:forEach var="i" begin="1" end="${totalPlaces >= 2 ? 2 : totalPlaces}">
                                                                <!-- Vérifier si la place est autorisée -->
                                                                <c:set var="autorisee" value="false"/>
                                                                <c:forEach var="p" items="${placesAutorisees}">
                                                                    <c:if test="${p == i}">
                                                                        <c:set var="autorisee" value="true"/>
                                                                    </c:if>
                                                                </c:forEach>

                                                                <label class="seat ${!autorisee ? 'seat-disabled' : ''}">
                                                                    <input type="checkbox"
                                                                            class="seat-checkbox"
                                                                           name="places"
                                                                           value="${i}"
                                                                           ${!autorisee ? 'disabled' : ''}>
                                                                    <span>${i}</span>
                                                                </label>
                                                            </c:forEach>
                                                        </div>

                                                        <!-- Lignes suivantes : à partir de la place 3, par blocs de 4 (3-6, 7-10, ...) -->
                                                        <c:if test="${totalPlaces > 2}">
                                                            <!-- Boucle sur les débuts de lignes : 3, 7, 11, ... -->
                                                            <c:forEach var="start" begin="3" end="${totalPlaces}" step="4">
                                                                <div class="seat-row">
                                                                    <!-- Pour chaque ligne, afficher de start à start+3 (max totalPlaces) -->
                                                                    <c:forEach var="i" begin="${start}" end="${start + 3 <= totalPlaces ? start + 3 : totalPlaces}">

                                                                        <!-- Vérifier si la place est autorisée -->
                                                                        <c:set var="autorisee" value="false"/>
                                                                        <c:forEach var="p" items="${placesAutorisees}">
                                                                            <c:if test="${p == i}">
                                                                                <c:set var="autorisee" value="true"/>
                                                                            </c:if>
                                                                        </c:forEach>

                                                                        <label class="seat ${!autorisee ? 'seat-disabled' : ''}">
                                                                            <input type="checkbox"
                                                                                    class="seat-checkbox"
                                                                                   name="places"
                                                                                   value="${i}"
                                                                                   ${!autorisee ? 'disabled' : ''}>
                                                                            <span>${i}</span>
                                                                        </label>

                                                                    </c:forEach>
                                                                </div>
                                                            </c:forEach>
                                                        </c:if>

                                                    </div>


                                                </div>
                                            </div>

                                        </div>

                                        <!-- Actions -->
                                        <div class="form-actions text-right">
                                            <a href="${pageContext.request.contextPath}/reservations/ajouter"
                                               class="btn btn-warning mr-1">
                                                <i class="ft-x"></i> Annuler
                                            </a>
                                            <button type="submit" class="btn btn-primary">
                                                <i class="la la-check-square-o"></i> Enregistrer
                                            </button>
                                        </div>

                                    </form>

                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </section>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>
<script>
    document.addEventListener("DOMContentLoaded", function () {

        const checkboxes = document.querySelectorAll(".seat-checkbox");
        const montantAffiche = document.getElementById("montantAffiche");

        // Map des tarifs par numero de place, fournie par le backend
        const tarifParPlace = JSON.parse('${tarifParPlaceJson}');

        function calculerMontant() {
            let total = 0;

            checkboxes.forEach(cb => {
                if (cb.checked) {
                    const num = parseInt(cb.value, 10);
                    const tarif = tarifParPlace[num] !== undefined ? parseFloat(tarifParPlace[num]) : 0;
                    total += isNaN(tarif) ? 0 : tarif;
                }
            });

            if (montantAffiche) {
                montantAffiche.value = total.toLocaleString('fr-FR') + " Ar";
            }
        }

        checkboxes.forEach(cb => {
            cb.addEventListener("change", calculerMontant);
        });
    });
</script>

</body>
</html>
