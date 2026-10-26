import { expect, test } from '@playwright/test';

test.describe('Port and Interface', () => {
  test('Port list renders', async ({ page }) => {
    await page.goto('/port/ports');
    await expect(page.getByRole('heading', { name: 'Ports' }).or(page.getByRole('heading', { name: 'Portes' }))).toBeVisible();
  });

  test('Port create form is reachable', async ({ page }) => {
    await page.goto('/port/ports/new');
    await expect(page.getByRole('form', { name: 'Port form' })).toBeVisible();
  });

  test('Interface list renders', async ({ page }) => {
    await page.goto('/port/interfaces');
    await expect(page.getByRole('heading', { name: 'Interfaces' }).or(page.getByRole('heading', { name: 'Interfacees' }))).toBeVisible();
  });

  test('Interface create form is reachable', async ({ page }) => {
    await page.goto('/port/interfaces/new');
    await expect(page.getByRole('form', { name: 'Interface form' })).toBeVisible();
  });

  test('Sub Interface list renders', async ({ page }) => {
    await page.goto('/port/sub-interfaces');
    await expect(page.getByRole('heading', { name: 'Sub Interfaces' }).or(page.getByRole('heading', { name: 'Sub Interfacees' }))).toBeVisible();
  });

  test('Sub Interface create form is reachable', async ({ page }) => {
    await page.goto('/port/sub-interfaces/new');
    await expect(page.getByRole('form', { name: 'Sub Interface form' })).toBeVisible();
  });

  test('Lag Group list renders', async ({ page }) => {
    await page.goto('/port/lag-groups');
    await expect(page.getByRole('heading', { name: 'Lag Groups' }).or(page.getByRole('heading', { name: 'Lag Groupes' }))).toBeVisible();
  });

  test('Lag Group create form is reachable', async ({ page }) => {
    await page.goto('/port/lag-groups/new');
    await expect(page.getByRole('form', { name: 'Lag Group form' })).toBeVisible();
  });

  test('Transceiver list renders', async ({ page }) => {
    await page.goto('/port/transceivers');
    await expect(page.getByRole('heading', { name: 'Transceivers' }).or(page.getByRole('heading', { name: 'Transceiveres' }))).toBeVisible();
  });

  test('Transceiver create form is reachable', async ({ page }) => {
    await page.goto('/port/transceivers/new');
    await expect(page.getByRole('form', { name: 'Transceiver form' })).toBeVisible();
  });
});
