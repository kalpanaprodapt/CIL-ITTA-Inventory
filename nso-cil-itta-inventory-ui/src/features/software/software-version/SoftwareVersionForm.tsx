import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { SOFTWARE_VERSION_RELEASE_TRAIN_OPTIONS, SOFTWARE_VERSION_CRITICALITY_OPTIONS, SoftwareVersionReleaseTrain, SoftwareVersionCriticality } from './types';
import { validateSoftwareVersion, cleanSoftwareVersionValues } from './schema';
import { SOFTWAREVERSION_DEFAULTS } from './types';
import type { SoftwareVersionFormValues, SoftwareVersionInput } from './types';

interface Props {
  initial?: SoftwareVersionFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: SoftwareVersionInput) => void;
  onCancel?: () => void;
}

export function SoftwareVersionForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<SoftwareVersionFormValues>({ ...SOFTWAREVERSION_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof SoftwareVersionFormValues>(key: K, value: SoftwareVersionFormValues[K]) {
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
    const result = validateSoftwareVersion(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanSoftwareVersionValues(values as Record<string, unknown>) as unknown as SoftwareVersionInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Software Version form">
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
      <FormField label="Image Id" htmlFor="imageId" error={errors.imageId} required={true}>
        <Input
          id="imageId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.imageId ?? ''}
          onChange={(v) => set('imageId', v)}
        />
      </FormField>
      <FormField label="Version String" htmlFor="versionString" error={errors.versionString} required={true}>
        <Input
          id="versionString"
          value={values.versionString ?? ''}
          maxLength={255}
          onChange={(v) => set('versionString', v)}
        />
      </FormField>
      <FormField label="Release Date" htmlFor="releaseDate" error={errors.releaseDate} required={false}>
        <DateInput
          id="releaseDate"
          value={values.releaseDate ?? ''}
          onChange={(v) => set('releaseDate', v)}
        />
      </FormField>
      <FormField label="End Of Support" htmlFor="endOfSupport" error={errors.endOfSupport} required={false}>
        <DateInput
          id="endOfSupport"
          value={values.endOfSupport ?? ''}
          onChange={(v) => set('endOfSupport', v)}
        />
      </FormField>
      <FormField label="Release Train" htmlFor="releaseTrain" error={errors.releaseTrain} required={false}>
        <Select
          id="releaseTrain"
          value={values.releaseTrain ?? ''}
          options={SOFTWARE_VERSION_RELEASE_TRAIN_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('releaseTrain', (v || undefined) as SoftwareVersionReleaseTrain | undefined)}
        />
      </FormField>
      <FormField label="Cve Count" htmlFor="cveCount" error={errors.cveCount} required={false}>
        <NumberInput
          id="cveCount"
          value={values.cveCount}
          step={1}
          onChange={(v) => set('cveCount', v)}
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
          options={SOFTWARE_VERSION_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as SoftwareVersionCriticality | undefined)}
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
