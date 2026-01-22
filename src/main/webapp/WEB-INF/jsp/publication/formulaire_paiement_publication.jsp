<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>

<body class="vertical-layout vertical-menu 2-columns fixed-navbar"
      data-open="click" data-menu="vertical-menu" data-col="2-columns">

<%@ include file="/WEB-INF/jsp/layout/sidebar.jsp"%>

<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="content-wrapper">

        <div class="content-header row">
            <div class="content-header-left col-md-6 col-12 mb-2">
                <h3 class="content-header-title">Paiement diffusion publication</h3>
            </div>
        </div>

        <div class="content-body">
            <section id="basic-form-layouts">
                <div class="row">
                    <div class="col-md-12">

                        <div class="card">
                            <div class="card-content collpase show">
                                <div class="card-body">

                                    <c:if test="${not empty errorMessage}">
                                        <div class="alert alert-danger">${errorMessage}</div>
                                    </c:if>

                                    <div class="mb-2">
                                        <strong>Date:</strong> ${etat.dateVoyage} &nbsp; | &nbsp;
                                        <strong>Heure:</strong> ${etat.heureDepart} &nbsp; | &nbsp;
                                        <strong>Societe:</strong> ${etat.societe} &nbsp; | &nbsp;
                                        <strong>Publication:</strong> ${etat.titre}
                                    </div>

                                    <div class="mb-2">
                                        <strong>Total a payer:</strong> ${etat.totalAPayer} &nbsp; | &nbsp;
                                        <strong>Deja paye:</strong> ${etat.montantPaye} &nbsp; | &nbsp;
                                        <strong>Reste:</strong> ${etat.resteAPayer}
                                    </div>

                                    <form method="post" action="${pageContext.request.contextPath}/publications/paiements/ajouter"
                                          class="form form-horizontal form-bordered">

                                        <input type="hidden" name="idVoyagePub" value="${idVoyagePub}" />

                                        <div class="form-body">
                                            <div class="form-group row">
                                                <label class="col-md-3 label-control">Montant a payer</label>
                                                <div class="col-md-9">
                                                    <input type="number" step="0.01" min="0.01" class="form-control" name="montant" required>
                                                </div>
                                            </div>

                                            <div class="form-group row">
                                                <label class="col-md-3 label-control">Date de paiement</label>
                                                <div class="col-md-9">
                                                    <input type="datetime-local" class="form-control" name="datePaiement" required>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-actions text-right">
                                            <a href="${pageContext.request.contextPath}/publications/paiements" class="btn btn-secondary mr-1">
                                                Retour
                                            </a>
                                            <button type="submit" class="btn btn-primary">
                                                Enregistrer paiement
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
</body>
</html>
