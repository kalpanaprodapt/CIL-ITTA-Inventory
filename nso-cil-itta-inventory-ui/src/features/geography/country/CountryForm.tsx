import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { COUNTRY_CRITICALITY_OPTIONS, CountryCriticality } from './types';
import { validateCountry, cleanCountryValues } from './schema';
import { COUNTRY_DEFAULTS } from './types';
import type { CountryFormValues, CountryInput } from './types';

interface Props {
  initial?: CountryFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: CountryInput) => void;
  onCancel?: () => void;
}

export function CountryForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<CountryFormValues>({ ...COUNTRY_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof CountryFormValues>(key: K, value: CountryFormValues[K]) {
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
    const result = validateCountry(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanCountryValues(values as Record<string, unknown>) as unknown as CountryInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Country form">
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
      <FormField label="Iso Code" htmlFor="isoCode" error={errors.isoCode} required={true}>
        <Input
          id="isoCode"
          value={values.isoCode ?? ''}
          maxLength={255}
          onChange={(v) => set('isoCode', v)}
        />
      </FormField>
      <FormField label="Iso Numeric" htmlFor="isoNumeric" error={errors.isoNumeric} required={false}>
        <NumberInput
          id="isoNumeric"
          value={values.isoNumeric}
          step={1}
          onChange={(v) => set('isoNumeric', v)}
        />
      </FormField>
      <FormField label="Dial Code" htmlFor="dialCode" error={errors.dialCode} required={false}>
        <Input
          id="dialCode"
          value={values.dialCode ?? ''}
          maxLength={255}
          onChange={(v) => set('dialCode', v)}
        />
      </FormField>
      <FormField label="Currency" htmlFor="currency" error={errors.currency} required={false}>
        <Input
          id="currency"
          value={values.currency ?? ''}
          maxLength={255}
          onChange={(v) => set('currency', v)}
        />
      </FormField>
      <FormField label="Regulatory Body" htmlFor="regulatoryBody" error={errors.regulatoryBody} required={false}>
        <Input
          id="regulatoryBody"
          value={values.regulatoryBody ?? ''}
          maxLength={255}
          onChange={(v) => set('regulatoryBody', v)}
        />
      </FormField>
      <FormField label="Timezone" htmlFor="timezone" error={errors.timezone} required={false}>
        <Input
          id="timezone"
          value={values.timezone ?? ''}
          maxLength={255}
          onChange={(v) => set('timezone', v)}
        />
      </FormField>
      <FormField label="Region" htmlFor="region" error={errors.region} required={false}>
        <Input
          id="region"
          value={values.region ?? ''}
          maxLength={255}
          onChange={(v) => set('region', v)}
        />
      </FormField>
      <FormField label="Owner" htmlFor="owner" error={errors.owner} required={false}>
        <Input
          id="owner"
          value={values.owner ?? ''}
          maxLength={255}
          onChange={(v) => set('owner', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={COUNTRY_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as CountryCriticality | undefined)}
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
