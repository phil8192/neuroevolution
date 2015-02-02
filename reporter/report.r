# load saved data 
load.data <- function(f) {
  read.table(f, header=F, sep=",", col.names=c("training", "testing"))
}

# plot training vs testing mse
plot.errors <- function(errors) {
  #par(mfrow=c(2, 1), oma=c(0, 0, 0, 0), omi=c(0, 0.4, 0, 0), 
  #    mar=c(0, 0, 0, 0))
  #plot(errors$training, type="l")
  #plot(errors$testing, type="l")
  par(bty="n")
  plot(errors$training, type="l", ylim=range(errors), xlab="epoch", ylab="mse", 
      main="errors", col="red")
  lines(errors$testing, col="blue")
  legend("topright", c("training", "testing"), col=c("red", "blue"), lty=1, 
      bty="n")
}

# plot to errors.png
errors <- load.data("reporter/model/errors.csv")
png("reporter/model/errors.png")
plot.errors(errors)
dev.off()

