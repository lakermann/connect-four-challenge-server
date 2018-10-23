new Vue({
    el: '#app',
    data: {
        swaggerUrl: '/swagger-ui.html',
        players: null,
        currentGame: {},
        currentGameID: null,
        scheduledGames: null,
        addToSchedule: {},
        games: null,
        gameDetails: {}
    },

    methods: {
        updateValues: function () {
            this.getCurrentGameId();
            this.getScheduledGames();
            this.getPlayers();
            this.getGames();

        },
        goToSwagger: function () {
            window.location.href = this.swaggerUrl;
        },
        getCurrentGameId: function () {
            if (this.currentGameID === null) {
                this.$http.get('/api/v1/ui/games/current/').then(response => {
                    const gameId = response.data.gameId;
                    if (typeof gameId !== "undefined"){
                        this.getCurrentGame(gameId);
                        this.currentGameID = gameId;
                    }
                }, function (error) {
                    console.log(error.statusText);
                });
            } else {
                this.getCurrentGame(this.currentGameID);
            }

        },
        getCurrentGame: function (gameId) {
            this.$http.get('/api/v1/ui/games/' + gameId).then(response => {
                this.currentGame = response.data;

                if (this.currentGame.finished === true) {
                    this.currentGameID = null;
                }
            }, function (error) {
                console.log(error.statusText);
            });
        },
        getPlayers: function () {
            this.$http.get('/api/v1/ui/players').then(response => {
                this.players = response.data;
            }, function (error) {
                console.log(error.statusText);
            });
        },
        getScheduledGames: function () {
            this.$http.get('/api/v1/ui/scheduled-games').then(response => {
                this.scheduledGames = response.data;
            }, function (error) {
                console.log(error.statusText);
            });
        },
        addScheduledGame: function () {
            const addScheduledGameRequest = {
                playerA: this.addToSchedule.playerA,
                playerB: this.addToSchedule.playerB,
                numberOfGames: this.addToSchedule.numberOfGames
            };
            this.$http.post('/api/v1/ui/scheduled-games', addScheduledGameRequest).then(response => {
                this.updateValues();
                console.log(response.data);
            }, function (error) {
                console.log(error.statusText);
            });

        },
        deleteScheduledGame: function (gameId) {
            this.$http.delete('/api/v1/ui/scheduled-games/' + gameId).then(response => {
                this.currentGameID = null;
                this.updateValues();
            }, function (error) {
                console.log(error.statusText);
            });
        },
        getGames: function () {
            this.$http.get('/api/v1/ui/games').then(response => {
                this.games = response.data;
            }, function (error) {
                console.log(error.statusText);
            });
        },
        getGameDetails: function (gameId) {
            this.$http.get('/api/v1/ui/games/' + gameId).then(response => {
                this.gameDetails = response.data;
            }, function (error) {
                console.log(error.statusText);
            });
        }
    },
    mounted: function () {
        this.$nextTick(function () {
            setInterval(this.updateValues, 2000);
        })
    }

});
