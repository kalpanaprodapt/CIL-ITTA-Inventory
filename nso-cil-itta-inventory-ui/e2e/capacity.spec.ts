import { expect, test } from '@playwright/test';

test.describe('Capacity Planning', () => {
  test('Capacity Plan list renders', async ({ page }) => {
    await page.goto('/capacity/capacity-plans');
    await expect(page.getByRole('heading', { name: 'Capacity Plans' }).or(page.getByRole('heading', { name: 'Capacity Planes' }))).toBeVisible();
  });

  test('Capacity Plan create form is reachable', async ({ page }) => {
    await page.goto('/capacity/capacity-plans/new');
    await expect(page.getByRole('form', { name: 'Capacity Plan form' })).toBeVisible();
  });

  test('Utilization Sample list renders', async ({ page }) => {
    await page.goto('/capacity/utilization-samples');
    await expect(page.getByRole('heading', { name: 'Utilization Samples' }).or(page.getByRole('heading', { name: 'Utilization Samplees' }))).toBeVisible();
  });

  test('Utilization Sample create form is reachable', async ({ page }) => {
    await page.goto('/capacity/utilization-samples/new');
    await expect(page.getByRole('form', { name: 'Utilization Sample form' })).toBeVisible();
  });

  test('Threshold Policy list renders', async ({ page }) => {
    await page.goto('/capacity/threshold-policies');
    await expect(page.getByRole('heading', { name: 'Threshold Policys' }).or(page.getByRole('heading', { name: 'Threshold Policyes' }))).toBeVisible();
  });

  test('Threshold Policy create form is reachable', async ({ page }) => {
    await page.goto('/capacity/threshold-policies/new');
    await expect(page.getByRole('form', { name: 'Threshold Policy form' })).toBeVisible();
  });

  test('Forecast Model list renders', async ({ page }) => {
    await page.goto('/capacity/forecast-models');
    await expect(page.getByRole('heading', { name: 'Forecast Models' }).or(page.getByRole('heading', { name: 'Forecast Modeles' }))).toBeVisible();
  });

  test('Forecast Model create form is reachable', async ({ page }) => {
    await page.goto('/capacity/forecast-models/new');
    await expect(page.getByRole('form', { name: 'Forecast Model form' })).toBeVisible();
  });

  test('Reservation list renders', async ({ page }) => {
    await page.goto('/capacity/reservations');
    await expect(page.getByRole('heading', { name: 'Reservations' }).or(page.getByRole('heading', { name: 'Reservationes' }))).toBeVisible();
  });

  test('Reservation create form is reachable', async ({ page }) => {
    await page.goto('/capacity/reservations/new');
    await expect(page.getByRole('form', { name: 'Reservation form' })).toBeVisible();
  });
});
