package endterm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EndtermApplication

fun main(args: Array<String>) {
	runApplication<EndtermApplication>(*args)
}
