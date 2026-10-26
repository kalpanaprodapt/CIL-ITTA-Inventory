import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { REGION_REGION_TYPE_OPTIONS, REGION_CRITICALITY_OPTIONS, RegionRegionType, RegionCriticality } from './types';
import { validateRegion, cleanRegionValues } from './schema';
import { REGION_DEFAULTS } from './types';
import type { RegionFormValues, RegionInput } from './types';

interface Props {
  initial?: RegionFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: RegionInput) => void;
  onCancel?: () => void;
}

export function RegionForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<RegionFormValues>({ ...REGION_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof RegionFormValues>(key: K, value: RegionFormValues[K]) {
    setValues((prev) => ({ ...prev, [key]: value }));
    if (errors[key as string]) {
      setErrors((prev) => {
        const next = { ...prev };
        delete next[key as string];
        return next;
      });
    }
  }

  function submit(ev: React.FormEvent) {
    ev.preventDefault();
    const result = validateRegion(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanRegionValues(values as Record<string, unknown>) as unknown as RegionInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Region form">
      {serverError ? <div role="alert" className="form-error">{serverError}</div> : null}
      <FormField label="Code" htmlFor="code" error={errors.code} required={true}>
        <Input
          id="code"
          value={values.code ?? ''}
          maxLength={255}
          onChange={(v) => set('code', v)}
        />
      </FormField>
      <FormField label="Name" htmlFor="name" error={errors.name} required={true}>
        <Input
          id="name"
          value={values.name ?? ''}
          maxLength={255}
          onChange={(v) => set('name', v)}
        />
      </FormField>
      <FormField label="Description" htmlFor="description" error={errors.description} required={false}>
        <TextArea
          id="description"
          value={values.description ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('description', v)}
        />
      </FormField>
      <FormField label="Status" htmlFor="status" error={errors.status} required={true}>
        <Select
          id="status"
          value={values.status ?? ''}
          options={LIFECYCLE_OPTIONS}
          allowEmpty={false}
          onChange={(v) => set('status', (v || undefined) as LifecycleStatus | undefined)}
        />
      </FormField>
      <FormField label="External Ref" htmlFor="externalRef" error={errors.externalRef} required={false}>
        <Input
          id="externalRef"
          value={values.externalRef ?? ''}
          maxLength={255}
          onChange={(v) => set('externalRef', v)}
        />
      </FormField>
      <FormField label="Country Id" htmlFor="countryId" error={errors.countryId} required={true}>
        <Input
          id="countryId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.countryId ?? ''}
          onChange={(v) => set('countryId', v)}
        />
      </FormField>
      <FormField label="Region Type" htmlFor="regionType" error={errors.regionType} required={false}>
        <Select
          id="regionType"
          value={values.regionType ?? ''}
          options={REGION_REGION_TYPE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('regionType', (v || undefined) as RegionRegionType | undefined)}
        />
      </FormField>
      <FormField label="Population" htmlFor="population" error={errors.population} required={false}>
        <NumberInput
          id="population"
          value={values.population}
          step={1}
          onChange={(v) => set('population', v)}
        />
      </FormField>
      <FormField label="Latitude" htmlFor="latitude" error={errors.latitude} required={false}>
        <NumberInput
          id="latitude"
          value={values.latitude}
          step={0.0001}
          onChange={(v) => set('latitude', v)}
        />
      </FormField>
      <FormField label="Longitude" htmlFor="longitude" error={errors.longitude} required={false}>
        <NumberInput
          id="longitude"
          value={values.longitude}
          step={0.0001}
          onChange={(v) => set('longitude', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={REGION_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as RegionCriticality | undefined)}
        />
      </FormField>
      <FormField label="Cost Center" htmlFor="costCenter" error={errors.costCenter} required={false}>
        <Input
          id="costCenter"
          value={values.costCenter ?? ''}
          maxLength={255}
          onChange={(v) => set('costCenter', v)}
        />
      </FormField>
      <FormField label="Notes" htmlFor="notes" error={errors.notes} required={false}>
        <TextArea
          id="notes"
          value={values.notes ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('notes', v)}
        />
      </FormField>
      <FormField label="Effective From" htmlFor="effectiveFrom" error={errors.effectiveFrom} required={false}>
        <DateInput
          id="effectiveFrom"
          value={values.effectiveFrom ?? ''}
          onChange={(v) => set('effectiveFrom', v)}
        />
      </FormField>
      <FormField label="Effective To" htmlFor="effectiveTo" error={errors.effectiveTo} required={false}>
        <DateInput
          id="effectiveTo"
          value={values.effectiveTo ?? ''}
          onChange={(v) => set('effectiveTo', v)}
        />
      </FormField>
      <div className="entity-form__actions">
        {onCancel ? (
          <Button type="button" variant="ghost" onClick={onCancel}>
            Cancel
          </Button>
        ) : null}
        <Button type="submit" disabled={busy}>
          {busy ? 'Saving…' : submitLabel}
        </Button>
      </div>
    </form>
  );
}
