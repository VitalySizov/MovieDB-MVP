package ru.vitalysizov.moviedb.domain.mapper.people

import org.threeten.bp.LocalDate
import ru.vitalysizov.moviedb.domain.mapper.Mapper
import ru.vitalysizov.moviedb.model.domain.person.PersonDetails
import ru.vitalysizov.moviedb.model.network.responses.people.PeopleDetailsResponse
import javax.inject.Inject

class PeopleDetailsMapper @Inject constructor() : Mapper<PeopleDetailsResponse, PersonDetails> {
    override fun map(from: PeopleDetailsResponse): PersonDetails {
        return PersonDetails(
            biography = from.biography.orEmpty(),
            birthDay = if (from.birthday != null) {
                LocalDate.parse(from.birthday)
            } else {
                LocalDate.of(0, 1, 1)
            },
            knowForDepartment = from.knowForDepartment.orEmpty(),
            deathDay = if (from.deathDay != null) {
                LocalDate.parse(from.deathDay)
            } else {
                LocalDate.of(0, 1, 1)
            },
            id = from.id ?: -1,
            name = from.name.orEmpty(),
            alsoKnownAs = from.alsoKnownAs ?: emptyList(),
            popularity = from.popularity ?: 0.0,
            gender = from.gender ?: 0,
            placeOfBirth = from.placeOfBirth.orEmpty(),
            profilePath = from.profilePath.orEmpty(),
            adult = from.adult ?: false,
            imdbId = from.imdbId.orEmpty(),
            homePage = from.homePage.orEmpty()
        )
    }
}