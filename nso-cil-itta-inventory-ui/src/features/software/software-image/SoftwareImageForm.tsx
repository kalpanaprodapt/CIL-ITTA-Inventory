import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { SOFTWARE_IMAGE_LIFECYCLE_STAGE_OPTIONS, SOFTWARE_IMAGE_CRITICALITY_OPTIONS, SoftwareImageLifecycleStage, SoftwareImageCriticality } from './types';
import { validateSoftwareImage, cleanSoftwareImageValues } from './schema';
import { SOFTWAREIMAGE_DEFAULTS } from './types';
import type { SoftwareImageFormValues, SoftwareImageInput } from './types';

interface Props {
  initial?: SoftwareImageFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: SoftwareImageInput) => void;
  onCancel?: () => void;
}

export function SoftwareImageForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<SoftwareImageFormValues>({ ...SOFTWAREIMAGE_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof SoftwareImageFormValues>(key: K, value: SoftwareImageFormValues[K]) {
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
    const result = validateSoftwareImage(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanSoftwareImageValues(values as Record<string, unknown>) as unknown as SoftwareImageInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Software Image form">
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
      <FormField label="Vendor Id" htmlFor="vendorId" error={errors.vendorId} required={true}>
        <Input
          id="vendorId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.vendorId ?? ''}
          onChange={(v) => set('vendorId', v)}
        />
      </FormField>
      <FormField label="Image Name" htmlFor="imageName" error={errors.imageName} required={true}>
        <Input
          id="imageName"
          value={values.imageName ?? ''}
          maxLength={255}
          onChange={(v) => set('imageName', v)}
        />
      </FormField>
      <FormField label="Size Mb" htmlFor="sizeMb" error={errors.sizeMb} required={false}>
        <NumberInput
          id="sizeMb"
          value={values.sizeMb}
          step={1}
          onChange={(v) => set('sizeMb', v)}
        />
      </FormField>
      <FormField label="Checksum" htmlFor="checksum" error={errors.checksum} required={false}>
        <Input
          id="checksum"
          value={values.checksum ?? ''}
          maxLength={255}
          onChange={(v) => set('checksum', v)}
        />
      </FormField>
      <FormField label="Platform" htmlFor="platform" error={errors.platform} required={false}>
        <Input
          id="platform"
          value={values.platform ?? ''}
          maxLength={255}
          onChange={(v) => set('platform', v)}
        />
      </FormField>
      <FormField label="Storage Path" htmlFor="storagePath" error={errors.storagePath} required={false}>
        <Input
          id="storagePath"
          value={values.storagePath ?? ''}
          maxLength={255}
          onChange={(v) => set('storagePath', v)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={SOFTWARE_IMAGE_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as SoftwareImageLifecycleStage | undefined)}
        />
      </FormField>
      <FormField label="Source System" htmlFor="sourceSystem" error={errors.sourceSystem} required={false}>
        <Input
          id="sourceSystem"
          value={values.sourceSystem ?? ''}
          maxLength={255}
          onChange={(v) => set('sourceSystem', v)}
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
          options={SOFTWARE_IMAGE_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as SoftwareImageCriticality | undefined)}
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
