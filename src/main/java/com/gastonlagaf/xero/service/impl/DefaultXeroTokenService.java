package com.gastonlagaf.xero.service.impl;

import com.gastonlagaf.xero.entity.XeroToken;
import com.gastonlagaf.xero.exception.XeroTokenNotFoundException;
import com.gastonlagaf.xero.model.XeroAuthCredentials;
import com.gastonlagaf.xero.repository.XeroTokenRepository;
import com.gastonlagaf.xero.service.XeroTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class DefaultXeroTokenService implements XeroTokenService {

    private final XeroTokenRepository tokenRepository;

    private final AtomicReference<XeroAuthCredentials> token = new AtomicReference<>();

    public DefaultXeroTokenService(XeroTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    @Transactional
    public XeroToken save(XeroToken xeroToken) {
        XeroToken newToken = findOne()
                .map(it -> {
                    it.setAccessToken(xeroToken.getAccessToken());
                    it.setRefreshToken(xeroToken.getRefreshToken());
                    it.setExpiry(xeroToken.getExpiry());
                    it.setUpdatedAt(LocalDate.now());
                    return tokenRepository.save(it);
                })
                .orElseGet(() -> tokenRepository.save(xeroToken));
        XeroAuthCredentials credentials = new XeroAuthCredentials(newToken.getAccessToken(), newToken.getTenantId());
        token.set(credentials);
        return newToken;
    }

    @Override
    @Transactional(readOnly = true)
    public XeroToken get() {
        return findOne().orElseThrow(XeroTokenNotFoundException::new);
    }

    @Override
    public XeroAuthCredentials getCredentials() {
        return Optional.ofNullable(token.get()).orElseThrow(XeroTokenNotFoundException::new);
    }

    private Optional<XeroToken> findOne() {
        return tokenRepository.findAll().stream().findFirst();
    }
}
