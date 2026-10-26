import { expect, test } from '@playwright/test';

test.describe('Radio and Mobile', () => {
  test('Radio Unit list renders', async ({ page }) => {
    await page.goto('/radio/radio-units');
    await expect(page.getByRole('heading', { name: 'Radio Units' }).or(page.getByRole('heading', { name: 'Radio Unites' }))).toBeVisible();
  });

  test('Radio Unit create form is reachable', async ({ page }) => {
    await page.goto('/radio/radio-units/new');
    await expect(page.getByRole('form', { name: 'Radio Unit form' })).toBeVisible();
  });

  test('Base Station list renders', async ({ page }) => {
    await page.goto('/radio/base-stations');
    await expect(page.getByRole('heading', { name: 'Base Stations' }).or(page.getByRole('heading', { name: 'Base Stationes' }))).toBeVisible();
  });

  test('Base Station create form is reachable', async ({ page }) => {
    await page.goto('/radio/base-stations/new');
    await expect(page.getByRole('form', { name: 'Base Station form' })).toBeVisible();
  });

  test('Antenna list renders', async ({ page }) => {
    await page.goto('/radio/antennas');
    await expect(page.getByRole('heading', { name: 'Antennas' }).or(page.getByRole('heading', { name: 'Antennaes' }))).toBeVisible();
  });

  test('Antenna create form is reachable', async ({ page }) => {
    await page.goto('/radio/antennas/new');
    await expect(page.getByRole('form', { name: 'Antenna form' })).toBeVisible();
  });

  test('Sector list renders', async ({ page }) => {
    await page.goto('/radio/sectors');
    await expect(page.getByRole('heading', { name: 'Sectors' }).or(page.getByRole('heading', { name: 'Sectores' }))).toBeVisible();
  });

  test('Sector create form is reachable', async ({ page }) => {
    await page.goto('/radio/sectors/new');
    await expect(page.getByRole('form', { name: 'Sector form' })).toBeVisible();
  });

  test('Backhaul Link list renders', async ({ page }) => {
    await page.goto('/radio/backhaul-links');
    await expect(page.getByRole('heading', { name: 'Backhaul Links' }).or(page.getByRole('heading', { name: 'Backhaul Linkes' }))).toBeVisible();
  });

  test('Backhaul Link create form is reachable', async ({ page }) => {
    await page.goto('/radio/backhaul-links/new');
    await expect(page.getByRole('form', { name: 'Backhaul Link form' })).toBeVisible();
  });
});
